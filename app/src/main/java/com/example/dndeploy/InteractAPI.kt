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
fun newCharacter(ownerID:String){
    val apiURL = "$url/createCharacter"
    val ownerJSON = JSONObject("""{"owner_ID":$ownerID}""")
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
fun retrieveCharacters(ownerID:String): ArrayList<CharacterData>{
    val apiURL = "$url/retrieveCharacters"
    val ownerJSON = JSONObject("""{"owner_ID":$ownerID}""")
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

//TODO: singular retrieval or find out how to pass intents by reference

//Ensure we can update then retrieve from database
fun levelUp(character: CharacterData){
    val leveledUpContents = character.characterContents?.copy(
        level = (character.characterContents.level!!.toInt() + 1).toString()
    )
    val apiURL = "$url/levelUp"
    val insertRow = CharacterRow(character.owner_ID,
        characterAttributesAdapter.toJson(leveledUpContents),
        character.character_ID)
    val insertJSON = JSONObject(
        """{"owner_ID":${insertRow.owner_ID},
            |character_JSON:${insertRow.character_JSON},
            |character_ID:${insertRow.character_ID}}"""
            .trimMargin()
    )
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    val body = (insertJSON.toString()).toRequestBody(JSON)
    val request = Request.Builder()
        .url(apiURL)
//        .url("http://10.0.2.2:3000/createCharacter")          //"abagail" local
        .post(body)
        .build()
    client.newCall(request).execute().use{response ->
        if(!response.isSuccessful) {throw IOException("Unexpected code $response")}
    }
}

//update database
//fun updateCharacter(){
//
//}

//take in owner1, owner1[items], owner2, owner2[items]
//fun tradeItems(aIems: List<Tradeable>, bItems:List<Tradeable>){
//
//}

//open class Tradeable(var ownerID: String?):Serializable{
//
//}
//class CharacterData(
//    owner_ID: String? = null,
//    val characterContents: CharacterAttributes? = null,
//    val character_ID: String? = null
//):Tradeable(owner_ID)

//API formatted character
data class CharacterRow(
    val owner_ID: String? = null,
    val character_JSON: String? = null,
    val character_ID: String? = null
): Serializable

//Internal formatted character
data class CharacterData(
    val owner_ID: String? = null,
    val characterContents: CharacterAttributes? = null,
    val character_ID: String? = null
):Serializable
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
