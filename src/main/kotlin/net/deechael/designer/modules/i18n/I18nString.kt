package net.deechael.designer.modules.i18n

import net.deechael.designer.extensions.text
import net.kyori.adventure.text.Component

class I18nString(val id: String) {

    fun asAdventure(): Component {
        return text(this)
    }

    override fun equals(other: Any?): Boolean {
        if (other == null)
            return false
        if (this === other)
            return true
        if (other !is I18nString)
            return false
        return this.id == other.id
    }

    override fun hashCode(): Int {
        return 32 * id.hashCode()
    }

}