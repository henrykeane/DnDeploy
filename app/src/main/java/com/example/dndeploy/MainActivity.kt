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
                    val dbResponse = ArrayList(retrieveCharacters(ownerID));
                    ownerIDIntent.putExtra("com.example.dndeploy.RESPONSE", dbResponse)
                    startActivity(ownerIDIntent)
                }
            }
        }
    }
}
private val client = OkHttpClient()
private val moshi = Moshi.Builder().build()
private val characterRowJSONAdapter = moshi.adapter(Array<CharacterRow>::class.java)

fun retrieveCharacters(ownerID:String): MutableList<String>{
//    print("Run")

    val ownerJSON = JSONObject("""{"ownerID":$ownerID}""")
    val json = "application/json; charset=utf-8".toMediaTypeOrNull()
    val body = (ownerJSON.toString()).toRequestBody(json)
    val request = Request.Builder()
//        .url("http://192.168.0.6:3000/retrieveCharacters")
        .url("http://10.0.2.2:3000/retrieveCharacters")
        .post(body)
        .build()
    client.newCall(request).execute().use { response ->
        if(!response.isSuccessful) throw IOException("Unexpected code $response")
        val sqlArray = characterRowJSONAdapter.fromJson(response.body!!.source())
        val size = sqlArray!!.size
        val characters = ArrayList<String>()//MutableList() {String()}
        for(row in 0 until size){
            //if owner id = ownerid add to characters TODO
            if((sqlArray.get(row).owner_ID).toString() == ownerID){
                val character = (sqlArray.get(row).character_JSON).toString();
                characters.add(character)
            }else   print("WHATS GOING ON")
        }
        return characters
    }
}
class CharacterRow {
    var owner_ID: String? = null
    var character_JSON: String? = null
}