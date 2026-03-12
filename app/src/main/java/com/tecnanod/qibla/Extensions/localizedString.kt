package com.tecnanod.qibla.Extensions

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

@Composable
fun localizedString(
    id: Int,
    vararg args: Any
): String {

    val locale = LocalAppLocale.current
    val context = LocalContext.current

    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)

    val localizedContext = context.createConfigurationContext(config)

    return localizedContext.resources.getString(id, *args)
}