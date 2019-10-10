package com.example.dndeploy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.Moshi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.io.IOException

class CharacterPoolActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_pool)
        val context = this

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        //intent passing
        val idTextView = findViewById<TextView>(R.id.owneridTextView)
        val ownerID = intent?.extras?.get("com.example.dndeploy.ID").toString()
        @SuppressWarnings("Unchecked cast")
        val characters =  intent?.extras?.get("com.example.dndeploy.RESPONSE")
                as (MutableList<CharacterData>)
        val idText = "ID: $ownerID"
        idTextView.text = idText

        val recyclerView = findViewById<RecyclerView>(R.id.characterPoolRecyclerView)
        recyclerView.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = CharacterPoolAdapter(characters.toTypedArray(), context)
        }

        val generateCharacterButton = findViewById<Button>(R.id.generateCharacterButton)
        generateCharacterButton.setOnClickListener{

            val refreshIntent = Intent(this, CharacterPoolActivity::class.java)
            doAsync{
                newCharacter(ownerID, context)
                refreshIntent.putExtra("com.example.dndeploy.ID", ownerID)
                val dbResponse = retrieveCharacters(ownerID, context);
                refreshIntent.putExtra("com.example.dndeploy.RESPONSE", dbResponse)
                finish()
                startActivity(refreshIntent)
            }
        }
    }
}