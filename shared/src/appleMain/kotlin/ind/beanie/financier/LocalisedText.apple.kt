package ind.beanie.financier

import platform.Foundation.NSLocale
import platform.Foundation.NSLog
import platform.Foundation.countryCode
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

actual class LocalisedText actual constructor(
    private val id: String,
    vararg localisedText: Pair<String, String>
) {
    private val textMap = localisedText.toMap()

    actual fun get(): String {
        val locale = NSLocale.currentLocale
        val combined = "${locale.languageCode}-${locale.countryCode}"
        return textMap[combined] ?: textMap[locale.languageCode] ?: notFound(combined)
    }

    actual fun get(locale: String): String {
        return textMap[locale] ?: notFound(locale)
    }

    private fun notFound(locale: String): String {
        val result = textMap["en"] ?: id
        NSLog("No translation found for $id in $locale")
        return result
    }

}