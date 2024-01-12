package com.example.bridges

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.GeoPoint

class BridgeDetailsActivity : AppCompatActivity() {
    lateinit var mapView: MapView
    lateinit var architectTextView: TextView
    lateinit var yearTextView: TextView
    lateinit var lengthTextView: TextView
    lateinit var widthTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bridgedetails)

        mapView = findViewById(R.id.mapView)
        architectTextView = findViewById(R.id.architectTextView)
        yearTextView = findViewById(R.id.yearTextView)
        lengthTextView = findViewById(R.id.lengthTextView)
        widthTextView = findViewById(R.id.widthTextView)
        val backButton = findViewById<Button>(R.id.backButton)

        backButton.setOnClickListener {
            finish()
        }


        val latitude = intent.getDoubleExtra("bridge_map_view_latitude", 0.0)
        val longitude = intent.getDoubleExtra("bridge_map_view_longitude", 0.0)
        Log.d("BridgeDetailsActivity", "Latitude: $latitude, Longitude: $longitude")
        val architect = intent.getStringExtra("bridge_architect")
        val length = intent.getIntExtra("bridge_length", 0)
        val width = intent.getIntExtra("bridge_width", 0)
        val buildYear = intent.getIntExtra("bridge_build_year", 0)

        mapView.getMapAsync { googleMap ->
            val bridgeLocation = LatLng(latitude, longitude)
            googleMap.addMarker(MarkerOptions().position(bridgeLocation).title("Bridge"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bridgeLocation, 10f))
        }

        architectTextView.text = architect
        lengthTextView.text = length.toString()
        widthTextView.text = width.toString()
        yearTextView.text = buildYear.toString()

    }

}