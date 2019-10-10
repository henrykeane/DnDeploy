package com.example.dndeploy

import android.app.AlertDialog
import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.io.Serializable
import java.util.concurrent.TimeUnit


private val baseClient = OkHttpClient()
private val client = baseClient.newBuilder()
    .connectTimeout (10, TimeUnit.SECONDS)
    .writeTimeout   (10, TimeUnit.SECONDS)
    .readTimeout    (10, TimeUnit.SECONDS)
    .callTimeout    (10, TimeUnit.SECONDS)
    .build()
private val moshi = Moshi.Builder().build()
val url = "https://d594674a.ngrok.io"                           //"caliber" ngrok
val characterAttributesAdapter: JsonAdapter<CharacterAttributes> =
    moshi.adapter(CharacterAttributes::class.java)
val characterRowAdapter: JsonAdapter<Array<CharacterRow>> =
    moshi.adapter(Array<CharacterRow>::class.java)

//Create a character and add it to the owner's roster in the database
fun newCharacter(ownerID:String, context: Context){
    val apiURL = "$url/createCharacter"
    val ownerJSON = JSONObject("""{"ownerID":$ownerID}""")
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    val body = (ownerJSON.toString()).toRequestBody(JSON)
    val request = Request.Builder()
        .url(apiURL)
//        .url("http://10.0.2.2:3000/createCharacter")          //"abagail" local
        .post(body)
        .build()
    client.newCall(request).execute().use{response ->
        if(!response.isSuccessful) {throw IOException("Unexpected code $response")}
    }
}

//Retrieve characters from an owner
fun retrieveCharacters(ownerID:String, context: Context): ArrayList<CharacterData>{
    val apiURL = "$url/retrieveCharacters"
    val ownerJSON = JSONObject("""{"ownerID":$ownerID}""")
    val json = "application/json; charset=utf-8".toMediaTypeOrNull()
    val body = (ownerJSON.toString()).toRequestBody(json)
    val request = Request.Builder()
        .url(apiURL)
//        .url("http://10.0.2.2:3000/retrieveCharacters")           //"abagail" local
        .post(body)
        .build()
    client.newCall(request).execute().use { response ->
        if(!response.isSuccessful) {throw IOException("Unexpected code $response")}
        val characterRows = characterRowAdapter.fromJson(response.body!!.source())
        val characterRoster = ArrayList<CharacterData>()


        for(row in characterRows!!){
            val characterAttribute =
                characterAttributesAdapter.fromJson(row.character_JSON!!)

            characterRoster.add(CharacterData(row.owner_ID, characterAttribute, row.character_ID))
        }
        return characterRoster
    }
}

data class CharacterData(
    val owner_ID: String? = null,
    val characterContents: CharacterAttributes? = null,
    val character_ID: String? = null
):Serializable
data class CharacterRow(
    val owner_ID: String? = null,
    val character_JSON: String? = null,
    val character_ID: String? = null
): Serializable
data class CharacterAttributes(
    val name:String?=null,
    val stats:CharacterStats?=null,
    val race:String?=null,
    val level:String?=null,
    val characterClass:String?=null,
    val spellbook:List<String>,
    val feats:List<String>,
    val items:List<String>,
    val lore:List<String>,
    val hp:String?=null,
    val prof:List<String>
):Serializable {
    data class CharacterStats(
        val str:String?=null,
        val dex:String?=null,
        val con:String?=null,
        val int:String?=null,
        val wis:String?=null,
        val cha:String?=null
    ): Serializable
}