package com.example.broar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bridges.BridgeDetailsActivity
import com.example.bridges.BridgesList
import com.example.bridges.MainActivity
import com.example.bridges.R

class BridgeRecycleAdapter(val context: Context, var bridges: List<BridgesList>) :
    RecyclerView.Adapter<BridgeRecycleAdapter.BridgeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BridgeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_bridges, parent, false)
        return BridgeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bridges.size
    }

    override fun onBindViewHolder(holder: BridgeViewHolder, position: Int) {
        val bridge = bridges[position]
        holder.bridgeName.text = bridge.name
        if (bridge.imageView != null) {
            holder.bridgeImage.setImageResource(bridge.imageView!!)
        }

    }


    inner class BridgeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bridgeName = view.findViewById<TextView>(R.id.nameTextView)
        val bridgeImage = view.findViewById<ImageView>(R.id.bridgeImageView)
        val bridgeButton = view.findViewById<Button>(R.id.bridgeButton)

        init {
            bridgeButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val bridge = bridges[position]
                    (context as MainActivity).bringDataBridgeDetails(bridge.name!!)


                }
            }
        }


    }
}