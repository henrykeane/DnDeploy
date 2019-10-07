package com.example.dndeploy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CharacterPoolActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_pool)


        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


        //intent passing

        val idTextView = findViewById<TextView>(R.id.owneridTextView)
        val ownerID = intent?.extras?.get("com.example.dndeploy.ID").toString()
        val characters = intent?.extras?.get("com.example.dndeploy.RESPONSE").toString()
        val idText = "ID: $ownerID"
        idTextView.text = idText

        val recyclerView = findViewById<RecyclerView>(R.id.characterPoolRecyclerView)
        recyclerView.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = CharacterPoolAdapter(ownerID,characters, context)
        }
    }
}
