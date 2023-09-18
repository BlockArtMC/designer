package net.deechael.designer.modules.backpack

import net.deechael.designer.Designer
import net.deechael.designer.extensions.registerListener
import net.deechael.designer.modules.backpack.listener.PlayerListener

object BackpackModule {

    fun init() {
        Designer.registerListener(PlayerListener)
    }

}