package com.example.dndeploy

import android.app.Application
import com.github.nkzawa.socketio.client.Socket
import com.github.nkzawa.socketio.client.IO
import java.net.URISyntaxException

class DraftApplication : Application() {

    val url = "https://851e8f58.ngrok.io"

    var mSocket: Socket? = null
    init{
        try{
            mSocket = IO.socket(url)
        }catch(e:URISyntaxException){
            throw RuntimeException(e)
        }
    }
    fun getSocket(): Socket? {
        return mSocket
    }
//    var mSocket:Socket? = null
//        private set
//
//    init {
//        try {
////            mSocket = IO.socket(url)
//            val result = IO.socket(url)
//            if(result != null){
//                mSocket=result as Socket?
//            }
//        } catch (e: URISyntaxException) {
//            throw RuntimeException(e)
//        }
//
//    }

}
