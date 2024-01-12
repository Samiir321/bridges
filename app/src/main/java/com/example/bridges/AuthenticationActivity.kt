package com.example.bridges

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore

class AuthenticationActivity : AppCompatActivity() {
    lateinit var db: FirebaseFirestore

    lateinit var auth: FirebaseAuth
    lateinit var emailView: EditText
    lateinit var passwordView: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authentication)
        db = Firebase.firestore
        auth = Firebase.auth
        emailView = findViewById(R.id.emailEditText)
        passwordView = findViewById(R.id.passwordEditText)

        val createButton = findViewById<Button>(R.id.createButton)

        createButton.setOnClickListener {
            createKonto()

        }
        val loggInBtn = findViewById<Button>(R.id.loggaInButton)
        loggInBtn.setOnClickListener {
            loggIN()
        }
        if (auth.currentUser != null) {
            goToRecyclerViewActivity()
        }

    }

    fun goToRecyclerViewActivity() {
        val intent = Intent(this@AuthenticationActivity, MainActivity::class.java)
        startActivity(intent)
    }

    fun loggIN() {
        val email = emailView.text.toString()
        val password = passwordView.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("!!!", "logged in ")
                    goToRecyclerViewActivity()
                } else {
                    Log.d("!!!", "not looged in  ${task.exception}")
                }

            }

    }
    fun copyBridgesToUserCollection() {
        val user = auth.currentUser
        if (user != null) {
            db.collection("bridges").get().addOnSuccessListener { result ->
                val bridgesFromDb = result.map { it.toObject(BridgesList::class.java) }
                for (bridge in bridgesFromDb) {
                    db.collection("users").document(user.uid).collection("bridges").add(bridge)
                }
            }
        }
    }


    fun createKonto() {
        val email = emailView.text.toString()
        val password = passwordView.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("!!!", "created")
                    copyBridgesToUserCollection()
                    goToRecyclerViewActivity()
                } else {
                    Log.d("!!!", "not created ${task.exception}")
                }

            }

    }
}