package com.tecnanod.qibla.ui

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.tecnanod.qibla.Extensions.LocaleManager
import com.tecnanod.qibla.R

@Composable
fun LanguageDialog(
    onDismiss: () -> Unit
) {

    val context = LocalContext.current

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {},

        title = {
            Text("Language")
        },

        text = {

            Column {

                LanguageItem("English") {

                    LocaleManager.change(context, "en")
                    onDismiss()
                }

                LanguageItem("Русский") {

                    LocaleManager.change(context, "ru")
                    onDismiss()
                }

                LanguageItem("Azərbaycan") {

                    LocaleManager.change(context, "az")
                    onDismiss()
                }

                LanguageItem("العربية") {

                    LocaleManager.change(context, "ar")
                    onDismiss()
                }
            }
        }
    )
}