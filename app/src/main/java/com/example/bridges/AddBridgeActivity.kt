package com.example.bridges

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddBridgeActivity : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    lateinit var nameEditText: EditText
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add)
        auth = Firebase.auth
        db = Firebase.firestore
        nameEditText = findViewById(R.id.addBridgeEditText)
        val btn = findViewById<Button>(R.id.saveBridgeBtn)
        btn.setOnClickListener {
            saveBridge()
            val intent = Intent(this@AddBridgeActivity, AddInformatioActivity::class.java)
            startActivity(intent)
        }
    }

    fun saveBridge() {
        val bridge = BridgesList(name = nameEditText.text.toString())
        nameEditText.setText(" ")
        val user = auth.currentUser
        if (user == null) {
            return
        }

        db.collection("users").document(user.uid).collection("bridges").add(bridge)


    }
}