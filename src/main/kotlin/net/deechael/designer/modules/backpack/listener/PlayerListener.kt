package net.deechael.designer.modules.backpack.listener

import net.deechael.designer.modules.backpack.handler.HotbarHandler
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

object PlayerListener : Listener {

    private val LAST_WEAPON_SLOT = mutableMapOf<UUID, Int>()

    @EventHandler
    fun quit(event: PlayerQuitEvent) {
        LAST_WEAPON_SLOT.remove(event.player.uniqueId)
    }

    @EventHandler(
        ignoreCancelled = true,
        priority = EventPriority.HIGHEST
    )
    fun join(event: PlayerJoinEvent) {
        event.player.inventory.heldItemSlot = 0
        LAST_WEAPON_SLOT[event.player.uniqueId] = 0
    }

    @EventHandler(
        ignoreCancelled = true,
        priority = EventPriority.HIGHEST
    )
    fun inventory(event: InventoryClickEvent) {
        val player = event.whoClicked
        if (player !is Player)
            return
        if (player.inventory != event.inventory)
            return
        event.isCancelled = true

    }

    @EventHandler(
        ignoreCancelled = true,
        priority = EventPriority.HIGHEST
    )
    fun hotbar(event: PlayerItemHeldEvent) {
        val slot = event.newSlot
        if (slot == 0 || slot == 1) {
            LAST_WEAPON_SLOT[event.player.uniqueId] = slot
            return
        }
        event.isCancelled = true
        event.player.inventory.heldItemSlot = LAST_WEAPON_SLOT[event.player.uniqueId]!!
        HotbarHandler.handleRawSlot(event.player, slot)
    }


}