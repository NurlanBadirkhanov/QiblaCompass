package com.tecnanod.qibla.Extensions

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

object LocaleManager {

    private const val PREF = "settings"
    private const val KEY_LANG = "language"

    private val _locale = MutableStateFlow(Locale.getDefault())
    val locale: StateFlow<Locale> = _locale

    fun load(context: Context) {

        val prefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

        val lang = prefs.getString(KEY_LANG, Locale.getDefault().language)!!

        _locale.value = Locale(lang)
    }

    fun change(context: Context, lang: String) {

        val prefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

        prefs.edit().putString(KEY_LANG, lang).apply()

        _locale.value = Locale(lang)
    }
}