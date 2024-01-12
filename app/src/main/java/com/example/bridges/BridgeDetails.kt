package com.example.bridges

import com.google.android.gms.maps.MapView
import com.google.firebase.firestore.GeoPoint

data class BridgeDetails(
    val MapView: GeoPoint = GeoPoint(0.0, 0.0),
    val Arkitekt: String = "",
    val Längd: Int = 0,
    val Bredd: Int = 0,
    val Byggår: Int = 0
)