package net.deechael.designer.modules.i18n

class I18nKeyword internal constructor(id: String) {

    private val id: String

    init {
        this.id = id.lowercase()
    }

    private val locales = mutableMapOf<Language, String>()

    fun get(language: Language = Language.EN_US): String {
        if (this.locales.containsKey(language))
            return this.locales[language]!!
        return this.locales[Language.EN_US]!!
    }

    fun set(language: Language, value: String): I18nKeyword {
        this.locales[language] = value
        return this
    }

    fun remove(language: Language) {
        this.locales.remove(language)
    }

    fun has(language: Language): Boolean {
        return this.locales.containsKey(language)
    }

    override fun equals(other: Any?): Boolean {
        if (other == null)
            return false
        if (this === other)
            return true
        if (other !is I18nKeyword)
            return false
        return this.id == other.id && this.locales == other.locales
    }

    override fun hashCode(): Int {
        return 33 * id.hashCode()
    }


}