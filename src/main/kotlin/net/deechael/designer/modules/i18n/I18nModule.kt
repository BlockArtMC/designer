package net.deechael.designer.modules.i18n

import net.deechael.designer.Designer
import net.deechael.designer.modules.i18n.listener.KeywordReplacer
import org.bukkit.Bukkit

object I18nModule {

    fun init() {
        Bukkit.getProtocolManager().registerListener(Designer, KeywordReplacer)
    }

}