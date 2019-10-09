package com.example.dndeploy

import com.squareup.moshi.Moshi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.io.Serializable


private val client = OkHttpClient()
private val moshi = Moshi.Builder().build()
private val characterRowAdapter = moshi.adapter(Array<CharacterRow>::class.java)
val url = "https://e62ea644.ngrok.io"

//Create a character and add it to the owner's roster in the database
fun newCharacter(ownerID:String){
    val apiURL = "$url/createCharacter"
    val ownerJSON = JSONObject("""{"ownerID":$ownerID}""")
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    val body = (ownerJSON.toString()).toRequestBody(JSON)
    val request = Request.Builder()
        .url(apiURL)   //"caliber" ngrok
//        .url("http://10.0.2.2:3000/createCharacter")          //"abagail" local
        .post(body)
        .build()
    client.newCall(request).execute()
}

//Retrieve characters from an owner
fun retrieveCharacters(ownerID:String): ArrayList<CharacterData>{
    val apiURL = "$url/retrieveCharacters"
    val ownerJSON = JSONObject("""{"ownerID":$ownerID}""")
    val json = "application/json; charset=utf-8".toMediaTypeOrNull()
    val body = (ownerJSON.toString()).toRequestBody(json)
    val request = Request.Builder()
        .url(apiURL)    //"caliber" ngrok
//        .url("http://10.0.2.2:3000/retrieveCharacters")           //"abagail" local
        .post(body)
        .build()
    client.newCall(request).execute().use { response ->
        if(!response.isSuccessful) throw IOException("Unexpected code $response")
        val characterRows = characterRowAdapter.fromJson(response.body!!.source())
        val characterRoster = ArrayList<CharacterData>()

        val characterAttributesAdapter =
            moshi.adapter(CharacterAttributes::class.java)

        for(row in characterRows!!){
            //TODO: Make another moshi converter
            val characterAttribute =
                characterAttributesAdapter.fromJson(row.character_JSON!!)

            characterRoster.add(CharacterData(row.owner_ID, characterAttribute, row.character_ID))
        }
//        println("Retval: " + retval )
//        return retval
//        val sketval: Array<CharacterRow>? = null
//        return characterRoster.toArray()
        return characterRoster
    }
}
//
//data class Character(
//    val owner_ID: String?,
//    val character_JSON: CharacterContents?,
//    val character_ID: String?)


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
    ): Serializable {
        data class CharacterStats(
            val str:String?=null,
            val dex:String?=null,
            val con:String?=null,
            val int:String?=null,
            val wis:String?=null,
            val cha:String?=null
        ): Serializable
    }

/*
data class CharacterRow(
    val owner_ID: String? = null,
    val character_JSON: CharacterContents? = null,
    val character_ID: String? = null
): Serializable{
    data class CharacterContents(
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
    ): Serializable {
        data class CharacterStats(
            val str:String?=null,
            val dex:String?=null,
            val con:String?=null,
            val int:String?=null,
            val wis:String?=null,
            val cha:String?=null
        ): Serializable
    }
}
*/


/*
    private val characterPreviewJSONAdapter = moshi.adapter(Array<Character>::class.java)
//    private val characters = ArrayList<Character>()


    init{
//        println("Preview JSON: $characterRow")
//        val characterArray = characterPreviewJSONAdapter.fromJson(characterRow)
//        println("Preview Array: $characterArray")
//        for(character in characterArray!!){
//            println("Adding $character")
//            characters.add(character)
//            println("Stat: ${character.name} and this ${character.stats?.str}")
//        }
//        println("Characters: $characters")
    }
 */

