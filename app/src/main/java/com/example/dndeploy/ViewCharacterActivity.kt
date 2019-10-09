package com.example.dndeploy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ViewCharacterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_character)

        //Just retrieve everything and put it into the stuff

        val character = intent?.extras?.get("com.example.dndeploy.CHARACTER")
                                        as CharacterData

        val thisCharacter = character.characterContents

        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val name = "${thisCharacter?.name}"
        nameTextView.text = name

        val hpTextView = findViewById<TextView>(R.id.hpTextView)
        val hp = "HP: ${thisCharacter?.hp}"
        hpTextView.text = hp

        val classLevelTextView = findViewById<TextView>(R.id.classLevelTextView)
        val classLevel = "Level ${thisCharacter?.level} ${thisCharacter?.characterClass}"
        classLevelTextView.text = classLevel

        val raceTextView = findViewById<TextView>(R.id.raceTextView)
        raceTextView.text = thisCharacter?.race

        val featsTextView = findViewById<TextView>(R.id.featsTextView)
        var feats = ""
        for(feat in thisCharacter!!.feats){
            feats += feat
        }
        featsTextView.text = feats

        val itemsTextView = findViewById<TextView>(R.id.itemsTextView)
        var items = ""
        for(item in thisCharacter.items){
            items += item
        }
        itemsTextView.text = items

        val loreTextView = findViewById<TextView>(R.id.loreTextView)
        var lore = ""
        for(lorepiece in thisCharacter.lore){
            lore += lorepiece
        }
        loreTextView.text = lore

        val profTextView = findViewById<TextView>(R.id.profTextView)
        var prof = ""
        for(individualProf in thisCharacter.prof){
            prof += individualProf + " "
        }
        profTextView.text = prof

        val spellbookTextView = findViewById<TextView>(R.id.spellbookTextView)
        var spellbook = ""
        for(spell in thisCharacter.spellbook){
            spellbook += spell
        }
        spellbookTextView.text = spellbook

        val strTextView = findViewById<TextView>(R.id.strTextView)
        strTextView.text = thisCharacter.stats?.str
        val dexTextView = findViewById<TextView>(R.id.dexTextView)
        dexTextView.text = thisCharacter.stats?.dex
        val conTextView = findViewById<TextView>(R.id.conTextView)
        conTextView.text = thisCharacter.stats?.con
        val intTextView = findViewById<TextView>(R.id.intTextView)
        intTextView.text = thisCharacter.stats?.int
        val wisTextView = findViewById<TextView>(R.id.wisTextView)
        wisTextView.text = thisCharacter.stats?.wis
        val chaTextView = findViewById<TextView>(R.id.chaTextView)
        chaTextView.text = thisCharacter.stats?.cha
    }
}