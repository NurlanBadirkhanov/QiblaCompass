package com.tecnanod.qibla.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tecnanod.qibla.Extensions.MenuEntry
import com.tecnanod.qibla.Extensions.localizedString
import com.tecnanod.qibla.R
import com.tecnanod.qibla.Extensions.openUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    onBack: () -> Unit
) {
    var showLanguageDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val socialItems = listOf(

        MenuEntry(
            icon = R.drawable.instagram,
            title = localizedString(R.string.instagram),
            url = "https://www.instagram.com/tecnanod/"
        ),

        MenuEntry(
            icon = R.drawable.tik,
            title = localizedString(R.string.tiktok),
            url = "https://tiktok.com/@tecnanod"
        ),

        MenuEntry(
            icon = R.drawable.linkedin,
            title = localizedString(R.string.linkedin),
            url = "https://www.linkedin.com/company/tecnanod/"
        ),

        MenuEntry(
            icon = R.drawable.tecnanod,
            title = localizedString(R.string.website),
            url = "https://nod.az"
        )
    )

    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {
                    Text(stringResource(R.string.menu))
                },

                navigationIcon = {

                    IconButton(onClick = onBack) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }

    ) { padding ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding),

            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 12.dp
            ),

            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {

            items(socialItems) { item ->

                MenuCard(
                    icon = item.icon,
                    title = item.title
                ) {

                    openUrl(context, item.url)
                }
            }

            item {

                Spacer(modifier = Modifier.height(12.dp))

                Divider()

                Spacer(modifier = Modifier.height(12.dp))

                MenuCard(
                    icon = R.drawable.translation,
                    title = localizedString(R.string.language)
                ) {
                    showLanguageDialog = true
                }
            }
        }
    }
    if (showLanguageDialog) {

        LanguageDialog(
            onDismiss = { showLanguageDialog = false }
        )
    }
}

