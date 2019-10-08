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

        val generateCharacterButton = findViewById<Button>(R.id.generateCharacterButton)
        generateCharacterButton.setOnClickListener{

            val refreshIntent = Intent(this, CharacterPoolActivity::class.java)
            doAsync{
                newCharacter(ownerID)
                refreshIntent.putExtra("com.example.dndeploy.ID", ownerID)
                //uses retrieveCharacters fun from MainActivity.kt
                val dbResponse = ArrayList(retrieveCharacters(ownerID));
                refreshIntent.putExtra("com.example.dndeploy.RESPONSE", dbResponse)
                finish()
                startActivity(refreshIntent)
            }
        }
    }
}
private val client = OkHttpClient()
private val moshi = Moshi.Builder().build()
private val characterRowJSONAdapter = moshi.adapter(Array<CharacterRow>::class.java)

//Bad function, for now just manual check the ownerID
fun newCharacter(ownerID:String){

    val ownerJSON = JSONObject("""{"ownerID":$ownerID}""")
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    val body = (ownerJSON.toString()).toRequestBody(JSON)
    val request = Request.Builder()
        .url("https://ce40826e.ngrok.io/createCharacter")
//        .url("http://10.0.2.2:3000/createCharacter")
        .post(body)
        .build()
    client.newCall(request).execute()
}