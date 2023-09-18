package net.deechael.designer

import net.deechael.designer.data.DataHolder
import net.deechael.designer.data.DataTable
import net.deechael.designer.data.mysql.MysqlDatabase
import net.deechael.designer.modules.backpack.BackpackModule
import net.deechael.designer.modules.i18n.I18nModule
import net.deechael.designer.modules.quest.QuestModule
import net.deechael.designer.modules.test.TestModule
import net.deechael.designer.storage.PlayerStorage
import org.bukkit.plugin.java.JavaPlugin

object Designer : JavaPlugin() {

    // object Storage {
//
    //     val DATABASE = MysqlDatabase.Builder.of()
    //         .host(System.getProperty("blockart.mariadb.host"))
    //         .port(System.getProperty("blockart.mariadb.port").toInt())
    //         .username(System.getProperty("blockart.mariadb.username"))
    //         .password(System.getProperty("blockart.mariadb.password"))
    //         .database("blockart")
    //         .tablePrefix("designer_")
    //         .build()
//
    //     private fun <T : DataHolder> table(clazz: Class<T>): DataTable<T> {
    //         if (DATABASE.hasTable(clazz))
    //             return DATABASE.findTable(clazz)!!
    //         return DATABASE.initializeTable(clazz).create()
    //     }
//
    //     object Tables {
//
    //         val PLAYER: DataTable<PlayerStorage> = table(PlayerStorage::class.java)
//
    //     }
//
    // }

    override fun onEnable() {
        I18nModule.init()
        BackpackModule.init()
        QuestModule.init()

        if (System.getProperty("blockart.test") != null)
            TestModule.init()
    }

}