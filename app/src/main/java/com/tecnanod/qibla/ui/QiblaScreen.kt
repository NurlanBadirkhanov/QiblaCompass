package com.tecnanod.qibla.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.tecnanod.qibla.Extensions.CompassSensor
import com.tecnanod.qibla.R
import com.tecnanod.qibla.Extensions.calculateQiblaDirection
import com.tecnanod.qibla.Extensions.localizedString

@Composable
fun QiblaScreen() {

    val context = LocalContext.current

    var qiblaDirection by remember { mutableStateOf<Float?>(null) }
    var phoneAngle by remember { mutableStateOf(0f) }
    var hasPermission by remember { mutableStateOf(false) }

    val smoothAngle by animateFloatAsState(phoneAngle)

    // ---------- PERMISSION ----------

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->
            hasPermission = granted
        }

    LaunchedEffect(Unit) {

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            hasPermission = true
        } else {
            permissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    // ---------- GPS ----------

    LaunchedEffect(hasPermission) {

        if (!hasPermission) return@LaunchedEffect

        val fusedClient =
            LocationServices.getFusedLocationProviderClient(context)

        fusedClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).addOnSuccessListener { location ->

            location?.let {

                qiblaDirection =
                    calculateQiblaDirection(
                        it.latitude,
                        it.longitude
                    )
            }
        }
    }

    // ---------- COMPASS SENSOR ----------

    DisposableEffect(Unit) {

        val compass = CompassSensor(context) {
            phoneAngle = it
        }

        compass.start()

        onDispose {
            compass.stop()
        }
    }

    // ---------- UI ----------

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0E1A2B)),
        contentAlignment = Alignment.Center
    ) {

        if (!hasPermission) {

            Text(
                text = localizedString(R.string.location_permission_required),
                color = Color.White
            )

        } else if (qiblaDirection == null) {

            Text(
                text = localizedString(R.string.loading_qibla),
                color = Color.White
            )

        } else {

            QiblaCompass(
                phoneAngle = smoothAngle,
                qiblaAngle = qiblaDirection!!
            )
        }
    }
}