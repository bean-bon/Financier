package ind.beanie.financier

import android.util.Log
import java.util.Locale

actual class LocalisedText actual constructor(
    private val id: String,
    vararg localisedText: Pair<String, String>
) {
    private val textMap = localisedText.toMap()
    actual fun get(): String {
        val locale = Locale.getDefault()
        val combined = "${locale.language}-${locale.country}"
        return textMap[combined] ?: textMap[locale.language] ?: notFound(combined)
    }

    actual fun get(locale: String): String {
        return textMap[locale] ?: notFound(locale)
    }

    private fun notFound(locale: String): String {
        val result = textMap["en"] ?: id
        Log.w("LocalisedText", "No translation found for $id using $locale")
        return result
    }

}