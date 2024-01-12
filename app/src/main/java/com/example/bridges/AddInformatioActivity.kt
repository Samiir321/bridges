package com.example.bridges

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddInformatioActivity : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    lateinit var architectView: EditText
    lateinit var yearView: EditText
    lateinit var lenghtView: EditText
    lateinit var widthView: EditText
    lateinit var LatitudeView: EditText
    lateinit var LongitudeView: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_bridge)



        db = Firebase.firestore

        architectView = findViewById(R.id.architectEditText)
        yearView = findViewById(R.id.yearEditText)
        lenghtView = findViewById(R.id.lenghtEditText)
        widthView = findViewById(R.id.widthEditText)
        LatitudeView = findViewById(R.id.LatitudeEditText)
        LongitudeView = findViewById(R.id.LongitudeEditText)
        val btn = findViewById<Button>(R.id.saveBtn)

        btn.setOnClickListener {

            saveBridgeInformation()
            val intent = Intent(this@AddInformatioActivity, MainActivity::class.java)
            startActivity(intent)
        }


    }

    fun saveBridgeInformation() {
        val latitude = LatitudeView.text.toString().toDoubleOrNull()
        val longitude = LongitudeView.text.toString().toDoubleOrNull()

        if (latitude != null && longitude != null) {
            val architect = architectView.text.toString()
            val year = yearView.text.toString().toIntOrNull()
            val length = lenghtView.text.toString().toIntOrNull()
            val width = widthView.text.toString().toIntOrNull()

            if (year != null && length != null && width != null) {
                val bridge =
                    BridgeDetails(GeoPoint(latitude, longitude), architect, length, width, year)
                db.collection("bridges").add(bridge)

                // Rensa textfälten efter att bron har sparats
                architectView.setText(" ")
                yearView.setText(" ")
                lenghtView.setText(" ")
                widthView.setText(" ")
                LatitudeView.setText(" ")
                LongitudeView.setText(" ")
            } else {
                Toast.makeText(
                    this,
                    "Kunde inte konvertera År, Längd eller Bredd till Int. 😓",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                this,
                "Kunde inte konvertera Latitude eller Longitude till Double. 😓",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}

