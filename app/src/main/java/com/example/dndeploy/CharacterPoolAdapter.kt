package com.example.dndeploy

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.character_pool_detail.view.*

class CharacterPoolAdapter(ownerID: String, characterPoolJSON: String, private val mCon: Context)
    :RecyclerView.Adapter<CharacterPoolAdapter.CharacterViewHolder>(){

    private val moshi = Moshi.Builder().build()
    private val characterPreviewJSONAdapter = moshi.adapter(Array<Character>::class.java)
    private val characters = ArrayList<Character>()


    init{
        println("Preview JSON: $characterPoolJSON")
        val characterArray = characterPreviewJSONAdapter.fromJson(characterPoolJSON)
        println("Preview Array: $characterArray")
        for(character in characterArray!!){
            println("Adding $character")
            characters.add(character)
            println("Stat: ${character.name} and this ${character.stats?.str}")
        }
        println("Characters: $characters")
    }

    class CharacterViewHolder(v: View):RecyclerView.ViewHolder(v){//}, View.OnClickListener{
        private var view = v
    }

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val characterSelector = holder.itemView.characterTextView
        val character = characters[position]

        characterSelector?.text = character.name
        characterSelector.setOnClickListener{
            val characterIntent = Intent(mCon, ViewCharacterActivity::class.java)
            characterIntent.putExtra("com.example.dndeploy.CHARACTER",character)
            mCon.startActivity(characterIntent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.character_pool_detail, parent, false))
    }
}