package net.deechael.designer.extensions

import net.deechael.designer.modules.i18n.I18nString
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.BuildableComponent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentBuilder
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEventSource
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import java.util.*
import java.util.function.Consumer
import java.util.function.Function

internal class I18nTextComponent(
    private val i18nString: I18nString,
    children: MutableList<out ComponentLike>,
    private val style: Style?
) : BuildableComponent<I18nTextComponent, I18nTextComponent.Builder> {

    private val children: List<Component>

    init {
        this.children = ComponentLike.asComponents(children, Component.IS_NOT_EMPTY)
    }

    override fun toBuilder(): Builder {
        return Builder(this)
    }

    override fun children(): List<Component> {
        return this.children
    }

    override fun children(children: MutableList<out ComponentLike>): I18nTextComponent {
        val filteredChildren = ComponentLike.asComponents(children, Component.IS_NOT_EMPTY)
        return I18nTextComponent(this.i18nString, filteredChildren, this.style)
    }

    override fun style(): Style {
        return this.style ?: Style.empty()
    }

    override fun style(style: Style): I18nTextComponent {
        val filteredChildren = ComponentLike.asComponents(children, Component.IS_NOT_EMPTY)
        return I18nTextComponent(this.i18nString, filteredChildren, style)
    }

    fun content(): I18nString {
        return this.i18nString
    }

    fun content(content: I18nString): I18nTextComponent {
        val filteredChildren = ComponentLike.asComponents(children, Component.IS_NOT_EMPTY)
        return I18nTextComponent(content, filteredChildren, this.style)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other !is I18nTextComponent)
            return false
        return children == other.children && style == other.style && i18nString.id == other.i18nString.id
    }

    override fun hashCode(): Int {
        var result = children.hashCode()
        result = 31 * result + style.hashCode()
        result = 31 * result + this.i18nString.id.hashCode()
        return result
    }

    override fun toString(): String {
        return this.i18nString.id
    }

    class Builder() : ComponentBuilder<I18nTextComponent, Builder> {
        private var children = mutableListOf<Component>()
        private var style: Style? = null
        private var styleBuilder: Style.Builder? = null

        private var content: I18nString? = null

        constructor(component: I18nTextComponent) : this() {
            val children: List<Component> = component.children()
            if (!children.isEmpty()) {
                this.children = ArrayList(children)
            }
            if (component.hasStyling()) {
                style = component.style()
            }
            this.content = component.content()
        }

        override fun append(component: Component): Builder {
            if (component === Component.empty()) return this
            prepareChildren()
            children.add(Objects.requireNonNull(component, "component"))
            return this
        }

        override fun append(vararg components: Component): Builder {
            return this.append(*components as Array<ComponentLike>)
        }

        override fun append(vararg components: ComponentLike): Builder {
            Objects.requireNonNull(components, "components")
            var prepared = false
            var i = 0
            val length = components.size
            while (i < length) {
                val component = Objects.requireNonNull(components[i], "components[?]").asComponent()
                if (component !== Component.empty()) {
                    if (!prepared) {
                        prepareChildren()
                        prepared = true
                    }
                    children.add(Objects.requireNonNull(component, "components[?]"))
                }
                i++
            }
            return this
        }

        override fun append(components: Iterable<ComponentLike>): Builder {
            Objects.requireNonNull(components, "components")
            var prepared = false
            for (like in components) {
                val component = Objects.requireNonNull(like, "components[?]").asComponent()
                if (component !== Component.empty()) {
                    if (!prepared) {
                        prepareChildren()
                        prepared = true
                    }
                    children.add(Objects.requireNonNull(component, "components[?]"))
                }
            }
            return this
        }

        private fun prepareChildren() {
            if (children === emptyList<Component>()) {
                children = mutableListOf()
            }
        }

        override fun applyDeep(consumer: Consumer<in ComponentBuilder<*, *>?>): Builder {
            this.apply(consumer)
            if (children === emptyList<Component>()) {
                return this as Builder
            }
            val it = children.listIterator()
            while (it.hasNext()) {
                val child = it.next() as? BuildableComponent<*, *> ?: continue
                val childBuilder = child.toBuilder()
                childBuilder.applyDeep(consumer)
                it.set(childBuilder.build())
            }
            return this as Builder
        }

        override fun mapChildren(function: Function<BuildableComponent<*, *>?, out BuildableComponent<*, *>?>): Builder {
            if (children === emptyList<Component>()) {
                return this as Builder
            }
            val it = children.listIterator()
            while (it.hasNext()) {
                val child = it.next() as? BuildableComponent<*, *> ?: continue
                val mappedChild = Objects.requireNonNull(function.apply(child), "mappedChild")!!
                if (child === mappedChild) {
                    continue
                }
                it.set(mappedChild)
            }
            return this as Builder
        }

        override fun mapChildrenDeep(function: Function<BuildableComponent<*, *>?, out BuildableComponent<*, *>?>): Builder {
            if (children === emptyList<Component>()) {
                return this as Builder
            }
            val it = children.listIterator()
            while (it.hasNext()) {
                val child = it.next() as? BuildableComponent<*, *> ?: continue
                val mappedChild = Objects.requireNonNull(function.apply(child), "mappedChild")!!
                if (mappedChild.children().isEmpty()) {
                    if (child === mappedChild) {
                        continue
                    }
                    it.set(mappedChild)
                } else {
                    val builder = mappedChild.toBuilder()
                    builder.mapChildrenDeep(function)
                    it.set(builder.build())
                }
            }
            return this
        }

        override fun children(): List<Component> {
            return Collections.unmodifiableList(children)
        }

        override fun style(style: Style): Builder {
            this.style = style
            styleBuilder = null
            return this
        }

        override fun style(consumer: Consumer<Style.Builder?>): Builder {
            consumer.accept(styleBuilder())
            return this
        }

        override fun build(): I18nTextComponent {
            return I18nTextComponent(this.content!!, this.children, this.buildStyle())
        }

        override fun font(font: Key?): Builder {
            styleBuilder().font(font)
            return this
        }

        override fun color(color: TextColor?): Builder {
            styleBuilder().color(color)
            return this
        }

        override fun colorIfAbsent(color: TextColor?): Builder {
            styleBuilder().colorIfAbsent(color)
            return this
        }

        override fun decoration(decoration: TextDecoration, state: TextDecoration.State): Builder {
            styleBuilder().decoration(decoration, state)
            return this
        }

        override fun decorationIfAbsent(decoration: TextDecoration, state: TextDecoration.State): Builder {
            styleBuilder().decorationIfAbsent(decoration, state)
            return this
        }

        override fun clickEvent(event: ClickEvent?): Builder {
            styleBuilder().clickEvent(event)
            return this
        }

        override fun hoverEvent(source: HoverEventSource<*>?): Builder {
            styleBuilder().hoverEvent(source)
            return this
        }

        override fun insertion(insertion: String?): Builder {
            styleBuilder().insertion(insertion)
            return this
        }

        override fun mergeStyle(that: Component, merges: Set<Style.Merge?>): Builder {
            styleBuilder().merge(Objects.requireNonNull(that, "component").style(), merges)
            return this
        }

        override fun resetStyle(): Builder {
            style = null
            styleBuilder = null
            return this
        }

        private fun styleBuilder(): Style.Builder {
            if (styleBuilder == null) {
                if (style != null) {
                    styleBuilder = style!!.toBuilder()
                    style = null
                } else {
                    styleBuilder = Style.style()
                }
            }
            return styleBuilder!!
        }

        protected fun hasStyle(): Boolean {
            return styleBuilder != null || style != null
        }

        protected fun buildStyle(): Style {
            return if (styleBuilder != null) {
                styleBuilder!!.build()
            } else if (style != null) {
                style!!
            } else {
                Style.empty()
            }
        }
    }

}

fun text(string: I18nString): Component {
    return I18nTextComponent.Builder().build()
}