package com.example.dndeploy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat.startActivity
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val sdkInt = android.os.Build.VERSION.SDK_INT;
//        if(sdkInt > 8){
//            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
//            StrictMode.setThreadPolicy(policy)
//        }

        val ownerIDBtn = findViewById<Button>(R.id.owner_idButton)
        ownerIDBtn.setOnClickListener{
            val ownerIDIntent = Intent(this, CharacterPoolActivity::class.java)
            val ownerIDtext = findViewById<EditText>(R.id.owner_idEditText)
            val ownerID = ownerIDtext.text.toString().toInt()

//            run(ownerID.toString(),this)
            doAsync{
                val dbResponse = ArrayList(run(ownerID.toString()));
                ownerIDIntent.putExtra("com.example.dndeploy.ID", ownerID)
                ownerIDIntent.putExtra("com.example.dndeploy.RESPONSE", dbResponse)
                startActivity(ownerIDIntent)
            }
        }
    }
}
private val client = OkHttpClient()
private val moshi = Moshi.Builder().build()
private val characterRowJSONAdapter = moshi.adapter(Array<characterRow>::class.java)

//Bad function, for now just manual check the ownerID
fun run(ownerID:String): MutableList<String>{
//    print("Run")
//    val requestBody = MultipartBody.Builder()
//        .setType(MultipartBody.FORM)
//        .addFormDataPart("ownerID",ownerID)
//        .build()
    val request = Request.Builder()
//        .url("http://192.168.0.6:3000/conTest")
        .url("http://10.0.2.2:3000/conTest")
//        .post(requestBody)
        .build()
    client.newCall(request).execute().use { response ->
        if(!response.isSuccessful) throw IOException("Unexpected code $response")
        val sqlArray = characterRowJSONAdapter.fromJson(response.body!!.source())
        val size = sqlArray!!.size
        val characters = MutableList(size) {String()}
        for(row in 0 until size){
            //if owner id = ownerid add to characters TODO
            if((sqlArray.get(row).owner_ID).toString() == ownerID){
                characters.add((sqlArray.get(row).character_JSON).toString())
                print(sqlArray.get(row).character_JSON).toString()
            }else   print("WHATS GOING ON")
        }


//        val ownerIDtext = findViewById<EditText>(R.id.owner_idEditText)
//        val ownerID = ownerIDtext.text.toString().toInt()

//        run(ownerID.toString(),this)
//            val dbResponse = ArrayList(run(ownerID.toString(),ownerIDIntent));
//            ownerIDIntent.putExtra("com.example.dndeploy.ID", ownerID)
//            ownerIDIntent.putExtra("com.example.dndeploy.RESPONSE", dbResponse)
//            startActivity(ownerIDIntent)


//        val ownerIDIntent = Intent(context, CharacterPoolActivity::class.java)
//
//        val characterArray = ArrayList(characters)
//        ownerIDIntent.putExtra("com.example.dndeploy.ID", ownerID)
//        ownerIDIntent.putExtra("com.example.dndeploy.RESPONSE", characterArray)
//        startActivity(context, ownerIDIntent, null)
        return characters
    }
}
class characterRow {
    var owner_ID: String? = null
    var character_JSON: String? = null
}