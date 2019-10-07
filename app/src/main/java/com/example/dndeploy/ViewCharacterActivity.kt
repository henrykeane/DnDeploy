package com.example.dndeploy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ViewCharacterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_character)

        //Just retrieve everything and put it into the stuff

        val character = intent?.extras?.get("com.example.dndeploy.CHARACTER") as Character
        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        nameTextView.text = character.name
        val hpTextView = findViewById<TextView>(R.id.hpTextView)
        hpTextView.text = character.hp
        val levelTextView = findViewById<TextView>(R.id.levelTextView)
        levelTextView.text = character.level
        val raceTextView = findViewById<TextView>(R.id.raceTextView)
        raceTextView.text = character.race
        val characterClassTextView = findViewById<TextView>(R.id.characterClassTextView)
        characterClassTextView.text = character.characterClass
//        val featsTextView = findViewById<TextView>(R.id.featsTextView)
//        featsTextView.text = character.feats
//        val itemsTextView = findViewById<TextView>(R.id.itemsTextView)
//        itemsTextView.text = character.items
//        val loreTextView = findViewById<TextView>(R.id.loreTextView)
//        loreTextView.text = character.lore
//        val profTextView = findViewById<TextView>(R.id.profTextView)
//        profTextView.text = character.prof
//        val spellbookTextView = findViewById<TextView>(R.id.spellbookTextView)
//        spellbookTextView.text = character.spellbook
        val strTextView = findViewById<TextView>(R.id.strTextView)
        strTextView.text = character.stats?.str
        val dexTextView = findViewById<TextView>(R.id.dexTextView)
        dexTextView.text = character.stats?.dex
        val conTextView = findViewById<TextView>(R.id.conTextView)
        conTextView.text = character.stats?.con
        val intTextView = findViewById<TextView>(R.id.intTextView)
        intTextView.text = character.stats?.int
        val wisTextView = findViewById<TextView>(R.id.wisTextView)
        wisTextView.text = character.stats?.wis
        val chaTextView = findViewById<TextView>(R.id.chaTextView)
        chaTextView.text = character.stats?.cha

//        val moshi = Moshi.Builder().build()
//        val characterPreviewJSONAdapter = moshi.adapter(Character::class.java)
//        val JSON = characterPreviewJSONAdapter.toJson(character)
//        println(JSON)
//        println(stringify(character))
    }
}

/*
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
 */
