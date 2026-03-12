package com.tecnanod.qibla.Extensions

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

fun calculateQiblaDirection(
    lat: Double,
    lon: Double
): Float {

    val kaabaLat = Math.toRadians(21.4225)
    val kaabaLon = Math.toRadians(39.8262)

    val userLat = Math.toRadians(lat)
    val userLon = Math.toRadians(lon)

    val dLon = kaabaLon - userLon

    val y = sin(dLon) * cos(kaabaLat)

    val x =
        cos(userLat) * sin(kaabaLat) -
        sin(userLat) * cos(kaabaLat) * cos(dLon)

    var bearing =
        Math.toDegrees(atan2(y, x))

    bearing = (bearing + 360) % 360

    return bearing.toFloat()
}