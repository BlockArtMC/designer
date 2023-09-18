package net.deechael.designer.modules.test

import net.deechael.designer.extensions.i18n
import net.deechael.designer.extensions.sendI18n
import net.deechael.designer.modules.i18n.I18nRegistry
import net.deechael.designer.modules.i18n.Language
import org.bukkit.Bukkit

object TestModule {

    fun init() {
        I18nRegistry.keyword("blockart.test")
            .set(Language.EN_US, "welcome to blockart test mode")
            .set(Language.ZH_CN, "欢迎来到方块艺术测试模式")
        Bukkit.getArtistCommandManager().registerCommand("blockart", Bukkit.getArtistCommandManager().createCommand("test") {
            Executor {
                this.getSender().sendMessage("Hello world!")
            }
            Literal("sub") {
                Executor {
                    this.getSender().sendI18n(i18n("blockart.test"))
                }
                Argument("name", Bukkit.getArtistCommandManager().getArgumentManager().string()) { nameFunc ->
                    Executor {
                        try {
                            this.getSender().sendMessage("your name is ${nameFunc()}")
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        })
    }

}