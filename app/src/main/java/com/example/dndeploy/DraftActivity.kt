package com.example.dndeploy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.github.nkzawa.emitter.Emitter
import org.json.JSONException

class DraftActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draft)

        val app = this.application as DraftApplication
        val socket = app.getSocket()
        socket?.on("draft pass", draftPass)

        val socketToConsoleButton = findViewById<Button>(R.id.socketToConsoleButton)

        socketToConsoleButton.setOnClickListener{
            val messageType = "chat message"
            val messageContents = "sample text"
            socket?.emit(messageType,messageContents)
        }
    }
    private val draftPass = object : Emitter.Listener {
    override fun call(vararg args: Any) {
            (this@DraftActivity).runOnUiThread(Runnable {
                val data = args[0] as String//as JSONObject
                val msg: String
                try {
                    msg = data//.getString("msg")
                } catch (e: JSONException) {
                    print(e)
                    return@Runnable
                }

                val returnValTextView = findViewById<TextView>(R.id.returnValTextView)
                returnValTextView.text = msg
            })
        }
    }
}
