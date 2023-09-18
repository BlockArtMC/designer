package net.deechael.designer.modules.i18n

object I18nRegistry {

    private val keywords = mutableMapOf<String, I18nKeyword>()

    fun keyword(id: String): I18nKeyword {
        return keywords.getOrPut(id.lowercase()) {
            I18nKeyword(id.lowercase())
        }
    }

}