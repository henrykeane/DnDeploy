package com.example.dndeploy

import android.app.PendingIntent.getActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject


class DraftActivity : AppCompatActivity() {
    var mSocket =  IO.socket("https://851e8f58.ngrok.io")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draft)

        mSocket.on("draft pass", draftPass)
        mSocket.connect()

        val socketToConsoleButton = findViewById<Button>(R.id.socketToConsoleButton)

        socketToConsoleButton.setOnClickListener{
            val messageType = "chat message"
            val messageContents = "sample text"
            mSocket.emit(messageType,messageContents)
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
