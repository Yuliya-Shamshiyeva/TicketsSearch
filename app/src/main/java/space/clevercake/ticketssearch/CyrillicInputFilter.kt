package space.clevercake.ticketssearch

import android.text.InputFilter
import android.text.Spanned


class CyrillicInputFilter : InputFilter {
    private val cyrillicPattern = "[а-яА-Я]*".toRegex()

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (source != null && !source.toString().matches(cyrillicPattern)) {
            return ""
        }
        return null
    }
}
