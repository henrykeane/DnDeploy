package com.example.dndeploy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.github.nkzawa.emitter.Emitter
import org.json.JSONException

class DraftLobbyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draft_lobby)

        val ownerID = intent?.extras?.get("com.example.dndeploy.ID").toString()


        val lobbyGoButton = findViewById<Button>(R.id.lobbyGoButton)
        lobbyGoButton.setOnClickListener{
            val lobby_idEditText = findViewById<EditText>(R.id.lobby_idEditText)
            val lobbyID = lobby_idEditText.text.toString()
            if(lobbyID != ""){

                val app = this.application as DraftApplication
                val socket = app.getSocket()
                socket?.on("valid lobby", validLobby)

                socket?.connect()

                val messageType = "lobby request"
                socket?.emit(messageType,lobbyID)
            }
        }
    }
    private val validLobby = object : Emitter.Listener {
        override fun call(vararg args: Any) {
            (this@DraftLobbyActivity).runOnUiThread(Runnable {
                val data = args[0] as String//as JSONObject
                val msg: String
                try {
                    msg = data//.getString("msg")
                } catch (e: JSONException) {
                    print(e)
                    return@Runnable
                }

                val ownerID = intent?.extras?.get("com.example.dndeploy.ID").toString()
                val draftActivityIntent = Intent(this@DraftLobbyActivity, DraftActivity::class.java)
                draftActivityIntent.putExtra("com.example.dndeploy.LOBBY_ID", msg)
                draftActivityIntent.putExtra("com.example.dndeploy.ID", ownerID)
                startActivity(draftActivityIntent)
            })
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        val app = this.application as DraftApplication
        val socket = app.getSocket()
        socket?.disconnect()
    }
}
