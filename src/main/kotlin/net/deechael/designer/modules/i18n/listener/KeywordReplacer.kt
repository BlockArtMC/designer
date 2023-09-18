package net.deechael.designer.modules.i18n.listener

import net.deechael.designer.extensions.getLanguage
import net.deechael.designer.extensions.i18n
import net.deechael.protocol.ProtocolListener
import net.deechael.protocol.packet.*
import org.bukkit.entity.Player

object KeywordReplacer: ProtocolListener {
    override fun receiving(player: Player, packet: Packet): Packet {
        return packet
    }

    override fun sending(player: Player, packet: Packet): Packet {
        when (packet) {
            is S2CSystemChatPacket -> {
                packet.adventureContent(packet.adventureContent().i18n(player.getLanguage()))
            }

            is S2CSetTitlePacket -> {
                packet.adventureContent(packet.adventureContent().i18n(player.getLanguage()))
            }

            is S2CSetSubtitlePacket -> {
                packet.adventureContent(packet.adventureContent().i18n(player.getLanguage()))
            }

            is S2CSetActionBarPacket -> {
                packet.adventureContent(packet.adventureContent().i18n(player.getLanguage()))
            }

            is S2CTabListPacket -> {
                packet.headerContent(packet.headerContent().i18n(player.getLanguage()))
                packet.footerContent(packet.footerContent().i18n(player.getLanguage()))
            }
        }
        return packet
    }

}