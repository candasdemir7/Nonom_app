package com.mustafacandasdemir.candasdemir_hw2.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mustafacandasdemir.candasdemir_hw2.R
import com.mustafacandasdemir.candasdemir_hw2.RecipeActivity
import com.mustafacandasdemir.candasdemir_hw2.model.Recipe
import com.mustafacandasdemir.candasdemir_hw2.util.Utils

class CustomRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<CustomRecyclerViewAdapter.RecyclerViewItemHolder>() {

    //kendim sonradan eklediklerim
    interface RecyclerAdapterInterface {
        fun displayItem(sc: Recipe)
    }

    lateinit  var adapterInterface: RecyclerAdapterInterface

    init{
        adapterInterface = context as RecyclerAdapterInterface // with that statement activities which will use the adapter are foreced to implement the interface
    }




    //***********DSADSA*D*SAD*


    private var recyclerItemValues = emptyList<Recipe>()

    fun setData(items:List<Recipe>){
        recyclerItemValues = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerViewItemHolder {
        val inflator = LayoutInflater.from(viewGroup.context)
        val itemView: View = inflator.inflate(R.layout.item_layout, viewGroup, false)
        return RecyclerViewItemHolder(itemView)
    }

    override fun onBindViewHolder(myRecyclerViewItemHolder: RecyclerViewItemHolder, position: Int) {
        val item = recyclerItemValues[position]

// Metin değerlerini set et
        myRecyclerViewItemHolder.tvItemRecipeName.text = item.name.toString()
        myRecyclerViewItemHolder.tvItemIngredientsName.text = item.ingredients.toString()

// Görselin dosya adını uzantısız bir şekilde al
        val imageName = item.img.substringBefore(".") // "manti.png" => "manti"

// Drawable'daki görselin kaynağını (resId) bul
        val resourceId = myRecyclerViewItemHolder.itemView.context.resources.getIdentifier(
            imageName, // Uzantısız dosya adı
            "drawable",
            myRecyclerViewItemHolder.itemView.context.packageName
        )



        //SONRADAN EKLEDİM DİALOGİÇİN
        myRecyclerViewItemHolder.itemLayout.setOnClickListener{
            adapterInterface.displayItem(item)
        }


// Glide kullanarak görseli yükle
        Glide.with(myRecyclerViewItemHolder.itemView.context)
            .load(resourceId) // Drawable resource ID'yi yükle
            .override(400)
            .into(myRecyclerViewItemHolder.imgItemRecipe)


        //sonradan eklendi

        myRecyclerViewItemHolder.itemView.setOnClickListener{
            var intent = Intent(context, RecipeActivity::class.java)

            intent.putExtra("name", item.name)
            intent.putExtra("ingredients", item.ingredients)
            intent.putExtra("details", item.details)
            intent.putExtra("img", item.img)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return recyclerItemValues.size
    }

    inner class RecyclerViewItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //parentLayoutla aynı işlevde
        lateinit var itemLayout: LinearLayout
        lateinit var tvItemRecipeName: TextView
        lateinit var tvItemIngredientsName: TextView
        //lateinit var btnItemDetail: TextView
        lateinit var imgItemRecipe: ImageView




        init {
            itemLayout = itemView.findViewById(R.id.itemLayout)
            tvItemRecipeName = itemView.findViewById(R.id.tvItemRecipeName)
            tvItemIngredientsName = itemView.findViewById(R.id.tvItemIngredientsName)
           // btnItemDetail = itemView.findViewById(R.id.btnItemDetail)
            imgItemRecipe = itemView.findViewById(R.id.imgItemRecipe)


            //sonradan ekledim ama item layouttda aynıymış
//            parentLayout = itemView.findViewById(R.id.itemLayout)
//            //*****
            //Event can be added here for each item also, or add event in onBindViewHolder method
//            imgItemRecipe.setOnClickListener{
//                var curentitemPosition= layoutPosition
//                Toast.makeText(context, recyclerItemValues[curentitemPosition].toString(), Toast.LENGTH_LONG).show()
//            }
        }
    }
}
