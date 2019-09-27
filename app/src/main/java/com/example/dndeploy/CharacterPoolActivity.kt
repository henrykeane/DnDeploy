package com.example.dndeploy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.TextView

class CharacterPoolActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_pool)


        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


        //intent passing

        val idTextView = findViewById<TextView>(R.id.owneridTextView)
        val id = "ID: " + intent?.extras?.get("com.example.dndeploy.ID").toString()
        idTextView.text = id
    }
}
