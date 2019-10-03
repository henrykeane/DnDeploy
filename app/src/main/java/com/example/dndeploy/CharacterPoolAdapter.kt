package com.example.dndeploy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.character_pool_detail.view.*

//class CharacterPoolAdapter(private val ownerID: String, private val characters: Array<String>)
class CharacterPoolAdapter(ownerID: String, previewJSON: String)
    :RecyclerView.Adapter<CharacterPoolAdapter.CharacterViewHolder>(){

    private val moshi = Moshi.Builder().build()
    private val characterPreviewJSONAdapter = moshi.adapter(Array<CharacterPreview>::class.java)
    private val characters = ArrayList<CharacterPreview>()


    init{
        println("Preview JSON: $previewJSON")
        val previewArray = characterPreviewJSONAdapter.fromJson(previewJSON)
        println("Preview Array: $previewArray")
        for(preview in previewArray!!){
            println("Adding $preview")
            characters.add(preview)
        }
        println("Characters: $characters")
        /*
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
                val character = (sqlArray.get(row).character_JSON).toString();
                characters.add(character)
            }else   print("WHATS GOING ON")
        }
        return characters
    }
         */
    }

    //TODO: Add the rest of the parameters. We're doing this bitch manually
    data class CharacterPreview(val name:String?=null)//,
//                                @Json(name="character_JSON") val characterJSON: String)



    class CharacterViewHolder(v: View):RecyclerView.ViewHolder(v){//}, View.OnClickListener{

        private var view = v

//        private var character: String? = null
//
//        init{
//            view.setOnClickListener(this)
//        }
//        override fun onClick(v: View?) {
//            Log.d("RecyclerView","CLICK!")
//        }
//        companion object{
//            private val PHOT_KEY = "PHOTO"
//        }
    }

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.itemView.characterTextView?.text = characters[position].name//characterName// characters.get(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.character_pool_detail, parent, false))
    }
}


/*
class CharacterPoolAdapter : RecyclerView.Adapter<CharacterPoolAdapter.CharacterViewHolder> {
    public static class CharacterViewHolder
        extends RecyclerView.ViewHolder{

    }

}
*/
/*
class SecondAdapter(items:Array<String>, dankness:Array<String>, descriptions:Array<String>):RecyclerView.Adapter<SecondAdapter.SecondViewHolder>() {
  internal var items:Array<String>
  internal var dankness:Array<String>
  internal var descriptions:Array<String>
  val itemCount:Int
  get() {
    return items.size
  }
  class SecondViewHolder(v:View):RecyclerView.ViewHolder(v), View.OnClickListener {
    var view:View
    var position:Int = 0
    init{
      view = v
      view.setOnClickListener(this)
    }
    fun setPosition(position:Int) {
      this.position = position
    }
    fun onClick(v:View) {
      val intent = Intent(view.getContext(), DetailActivity::class.java)
      intent.putExtra("com.example.relearnandroid.ITEM_INDEX", position)
      println(position)
      v.getContext().startActivity(intent)
    }
  }
  init{
    this.items = items
    this.dankness = dankness
    this.descriptions = descriptions
  }
  fun onCreateViewHolder(parent:ViewGroup, viewType:Int):SecondAdapter.SecondViewHolder {
    val v = (LayoutInflater.from(parent.getContext())
             .inflate(R.layout.my_recyclerview_detail, parent, false))
    val vh = SecondViewHolder(v)
    return vh
  }
  fun onBindViewHolder(holder:SecondViewHolder, position:Int) {
    val nameTextView = holder.view.findViewById(R.id.nameTextView) as TextView
    nameTextView.setText(items[position])
    val descriptionTextView = holder.view.findViewById(R.id.descriptionTextView) as TextView
    descriptionTextView.setText(dankness[position])
    val danknessTextView = holder.view.findViewById(R.id.danknessTextView) as TextView
    danknessTextView.setText(descriptions[position])
    holder.setPosition(position)
  }
}
 */