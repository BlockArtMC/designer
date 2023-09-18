package net.deechael.designer.modules.quest.base

import net.deechael.designer.modules.i18n.I18nString

abstract class QuestBase {

    abstract fun getId(): String

    abstract fun getTitle(): I18nString

}