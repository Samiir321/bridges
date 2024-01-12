package com.example.bridges

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.broar.BridgeRecycleAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    lateinit var adapter: BridgeRecycleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth


        val bridges = mutableListOf<BridgesList>(
            BridgesList(R.drawable.royal_gorge, "Royal Gorge Bridge"),
            BridgesList(R.drawable.si_o_se_pol_bridge, "Si o se pol Bridge"),
            BridgesList(R.drawable.sydney_harbour, "Sydney harbour Bridge"),
            BridgesList(R.drawable.vascodagama, "Vascodagama Bridge"),
            BridgesList(R.drawable.rialto, "Rialto Bridge"),
            BridgesList(R.drawable.chapel, "Chapel Bridge"),
            BridgesList(R.drawable.akashi_kaikyo, "Akashi kaikyo Bridge"),
            BridgesList(R.drawable.tower, "Tower Bridge"),
            BridgesList(R.drawable.golden_gate, "Golden gate Bridge"),
            BridgesList(R.drawable.bay, "Bay Bridge")
        )


        adapter = BridgeRecycleAdapter(this, bridges)
        val recycler = findViewById<RecyclerView>(R.id.listBridgeView)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        Handler().postDelayed({
            bringDataBridges()
        }, 3000)

        val createAccButton = findViewById<FloatingActionButton>(R.id.createAccButton)
        createAccButton.setOnClickListener {
            Log.d("MainActivity", "FloatingActionButton clicked!")
            val intent = Intent(this, AuthenticationActivity::class.java)
            startActivity(intent)
        }
        val addBtn = findViewById<FloatingActionButton>(R.id.addButton)
        addBtn.setOnClickListener {
            val intent = Intent(this, AddBridgeActivity::class.java)
            startActivity(intent)

        }
        val loggOutBtn = findViewById<FloatingActionButton>(R.id.loggOutBtn)
        loggOutBtn.setOnClickListener {
            loggOut()
        }

    }

    fun loggOut() {
        auth.signOut()
        db.collection("bridges").get().addOnSuccessListener { result ->
            val bridgesFromDb = result.map { it.toObject(BridgesList::class.java) }
            adapter.bridges = bridgesFromDb
            adapter.notifyDataSetChanged()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    fun bringDataBridges() {
        val user = auth.currentUser
        if (user != null) {
            db.collection("users").document(user.uid).collection("bridges").get()
                .addOnSuccessListener { result ->
                    val bridgesFromDb = result.map { it.toObject(BridgesList::class.java) }
                    adapter.bridges = bridgesFromDb
                    adapter.notifyDataSetChanged()
                }
        }
    }

    fun bringDataBridgeDetails(bridgeName: String) {
        db.collection("bridgeDetails").document(bridgeName).get().addOnSuccessListener { document ->
            val bridgeDetails = document.toObject(BridgeDetails::class.java)
            val intent = Intent(this, BridgeDetailsActivity::class.java)



            intent.putExtra("bridge_architect", bridgeDetails?.Arkitekt)
            intent.putExtra("bridge_length", bridgeDetails?.Längd)
            intent.putExtra("bridge_width", bridgeDetails?.Bredd)
            intent.putExtra("bridge_build_year", bridgeDetails?.Byggår)
            intent.putExtra("bridge_map_view_latitude", bridgeDetails?.MapView?.latitude)
            intent.putExtra("bridge_map_view_longitude", bridgeDetails?.MapView?.longitude)

            startActivity(intent)
        }
    }


}