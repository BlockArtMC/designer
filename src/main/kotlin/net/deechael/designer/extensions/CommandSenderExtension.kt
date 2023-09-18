package net.deechael.designer.extensions

import net.deechael.designer.modules.i18n.Language
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

fun CommandSender.sendI18n(component: Component) {
    this.sendMessage(component.i18n(this.getLanguage()))
}

fun CommandSender.getLanguage(): Language {
    if (this is Player) {
        return this.getLanguage()
    }
    return Language.EN_US
}