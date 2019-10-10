package com.example.dndeploy

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.character_pool_detail.view.*

class CharacterPoolAdapter(private val characters: Array<CharacterData>?, private val mCon: Context)
    :RecyclerView.Adapter<CharacterPoolAdapter.CharacterViewHolder>(){

    class CharacterViewHolder(v: View):RecyclerView.ViewHolder(v){
//        private var view = v
    }

    override fun getItemCount() = characters!!.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val characterSelector = holder.itemView
        val character = characters!![position]

        characterSelector.characterNameTextView?.text = character.characterContents?.name
        characterSelector.characterClassTextView?.text = character.characterContents?.characterClass
        characterSelector.setOnClickListener{
            val characterIntent = Intent(mCon, ViewCharacterActivity::class.java)
            characterIntent.putExtra("com.example.dndeploy.CHARACTER",character)
            mCon.startActivity(characterIntent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.character_pool_detail, parent, false))
    }
}