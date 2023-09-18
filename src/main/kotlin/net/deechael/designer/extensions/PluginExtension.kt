package net.deechael.designer.extensions

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

fun Plugin.registerListener(listener: Listener) {
    Bukkit.getPluginManager().registerEvents(listener, this)
}