package com.tecnanod.qibla.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import com.tecnanod.qibla.Extensions.LocalAppLocale
import com.tecnanod.qibla.Extensions.LocaleManager
import com.tecnanod.qibla.ui.theme.QiblaTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            LocaleManager.load(this@MainActivity)
        }


        setContent {
            enableEdgeToEdge()

            val context = LocalContext.current

            LaunchedEffect(Unit) {
                LocaleManager.load(context)
            }

            val locale by LocaleManager.locale.collectAsState()

            CompositionLocalProvider(
                LocalAppLocale provides locale
            ) {

                QiblaTheme {
                    QiblaScreen()
                }
            }
        }
    }
}



