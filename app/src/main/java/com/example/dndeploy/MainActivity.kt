package com.example.dndeploy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.squareup.moshi.Moshi
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ownerIDBtn = findViewById<Button>(R.id.owner_idButton)
        ownerIDBtn.setOnClickListener{
            val ownerIDEditText = findViewById<EditText>(R.id.owner_idEditText)
            val ownerID = ownerIDEditText.text.toString()
            if(ownerID != ""){
                val ownerIDIntent = Intent(this, CharacterPoolActivity::class.java)
                doAsync{
                    ownerIDIntent.putExtra("com.example.dndeploy.ID", ownerID)
                    val dbResponse = retrieveCharacters(ownerID);
                    ownerIDIntent.putExtra("com.example.dndeploy.RESPONSE", dbResponse)
                    ownerIDIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(ownerIDIntent)
                }
            }
        }
    }
}