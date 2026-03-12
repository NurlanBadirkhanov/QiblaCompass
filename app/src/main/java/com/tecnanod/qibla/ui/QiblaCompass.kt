package com.tecnanod.qibla.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tecnanod.qibla.Extensions.localizedString
import com.tecnanod.qibla.R
import kotlin.math.abs
import kotlin.math.min

fun angleDifference(a: Float, b: Float): Float {
    val diff = abs(a - b)
    return min(diff, 360 - diff)
}

@Composable
fun QiblaCompass(
    phoneAngle: Float,
    qiblaAngle: Float
) {

    val diff = angleDifference(phoneAngle, qiblaAngle)

    var isQibla by remember { mutableStateOf(false) }

    // hysteresis
    if (!isQibla && diff < 2f) {
        isQibla = true
    }

    if (isQibla && diff > 4f) {
        isQibla = false
    }

    var showMenu by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(Color(0xFF0E1A2B)),
        contentAlignment = Alignment.Center
    ) {

        if (showMenu) {

            MenuScreen(
                onBack = { showMenu = false }
            )

        } else {

            TopRightMenu(
                onOpenMenu = { showMenu = true }
            )

            AnimatedContent(
                targetState = isQibla,
                label = ""
            ) { found ->

                if (found) {

                    Image(
                        painter = painterResource(R.drawable.kaaba),
                        contentDescription = null,
                        modifier = Modifier.size(220.dp)
                    )

                } else {

                    Image(
                        painter = painterResource(R.drawable.direction),
                        contentDescription = null,
                        modifier = Modifier
                            .size(300.dp)
                            .rotate(qiblaAngle - phoneAngle)
                    )
                }
            }

            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {

                Text(
                    text = localizedString(R.string.qibla_angle, qiblaAngle.toInt()),
                    color = Color.White
                )

                Text(
                    text = localizedString(R.string.turn_phone),
                    color = Color.LightGray
                )
            }
        }
    }
}
@Composable
fun TopRightMenu(
    onOpenMenu: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp),

        contentAlignment = Alignment.TopEnd
    ) {

        IconButton(
            onClick = onOpenMenu
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = Color(0xFFE0E0E0)
            )
        }
    }
}