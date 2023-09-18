package net.deechael.designer.extensions

import net.deechael.designer.modules.i18n.Language
import org.bukkit.entity.Player

fun Player.getLanguage(): Language {
    return Language.valueOf(this.locale().toLanguageTag().replace("-", "_").uppercase())
}