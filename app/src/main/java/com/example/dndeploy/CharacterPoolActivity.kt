package com.example.dndeploy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync

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
        val characters =  intent?.extras?.get("com.example.dndeploy.RESPONSE")
                as (MutableList<CharacterData>)
        val idText = "ID: $ownerID"
        idTextView.text = idText

        val recyclerView = findViewById<RecyclerView>(R.id.characterPoolRecyclerView)
        recyclerView.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = CharacterPoolAdapter(characters.toTypedArray(), context)
        }

        val draftButton = findViewById<Button>(R.id.draftButton)
        draftButton.setOnClickListener{
            val draftIntent = Intent(this,DraftLobbyActivity::class.java)
            draftIntent.putExtra("com.example.dndeploy.ID", ownerID)
            draftIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(draftIntent)
        }

        //TODO: Implement swipe-to-refresh interface OR webhook api

        val generateCharacterButton = findViewById<Button>(R.id.generateCharacterButton)
        generateCharacterButton.setOnClickListener{

            val refreshIntent = Intent(this, CharacterPoolActivity::class.java)
            doAsync{
                newCharacter(ownerID)
                refreshIntent.putExtra("com.example.dndeploy.ID", ownerID)
                val dbResponse = retrieveCharacters(ownerID);
                refreshIntent.putExtra("com.example.dndeploy.RESPONSE", dbResponse)
                refreshIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                finish()
                startActivity(refreshIntent)
            }
        }
    }

    //TODO: Handle back button logic
    //The following code works, I just don't know what to do with it
//    override fun onRestart(){
//        super.onRestart()
//        val idTextView = findViewById<TextView>(R.id.owneridTextView)
//        val testString = "Welcome back"
//        idTextView.text = testString
//
//        val ownerID = intent?.extras?.get("com.example.dndeploy.ID").toString()
//        val refreshIntent = Intent(this, CharacterPoolActivity::class.java)
//        doAsync{
//            newCharacter(ownerID)
//            refreshIntent.putExtra("com.example.dndeploy.ID", ownerID)
//            val dbResponse = retrieveCharacters(ownerID);
//            refreshIntent.putExtra("com.example.dndeploy.RESPONSE", dbResponse)
//            finish()
//            startActivity(refreshIntent)
//        }
//    }
}