package com.tecnanod.qibla.Extensions

import androidx.compose.runtime.staticCompositionLocalOf
import java.util.Locale

val LocalAppLocale = staticCompositionLocalOf {
    Locale.getDefault()
}