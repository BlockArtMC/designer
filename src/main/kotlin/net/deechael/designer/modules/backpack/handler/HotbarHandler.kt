package net.deechael.designer.modules.backpack.handler

import org.bukkit.entity.Player

object HotbarHandler {

    fun handleRawSlot(player: Player, rawSlot: Int) {
        this.handleSlot(player, rawSlot + 1)
    }

    fun handleSlot(player: Player, slot: Int) {
        if (slot == 3 || slot == 4) {
            this.handleGadget(player, slot - 3)
        } else if (slot == 5) {
            this.openMenu(player)
        } else if (slot == 6) {
            this.openBackpack(player)
        } else {
            this.handleSkill(player, slot - 7)
        }
    }

    fun handleGadget(player: Player, index: Int) {
        // TODO
    }

    fun handleSkill(player: Player, index: Int) {
        // TODO - with artist api
    }

    fun openMenu(player: Player) {

    }

    fun openBackpack(player: Player) {

    }

}