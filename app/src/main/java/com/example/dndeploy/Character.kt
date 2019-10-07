package com.example.dndeploy

import com.squareup.moshi.Json
import java.io.Serializable

data class Character(val name:String?=null,
                     val hp:String?=null,
                     val stats:CharacterStats?=null,
                     val race:String?=null,
                     val level:String?=null,
                     @Json(name="class")val characterClass:String?=null,
                     val spellbook:List<String>,
                     val feats:List<String>,
                     val items:List<String>,
                     val lore:List<String>,
                     val prof:List<String>): Serializable {
    data class CharacterStats(val str:String?=null,
                              val dex:String?=null,
                              val con:String?=null,
                              val int:String?=null,
                              val wis:String?=null,
                              val cha:String?=null): Serializable
}