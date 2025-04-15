package com.mustafacandasdemir.candasdemir_hw2.adapter

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mustafacandasdemir.candasdemir_hw2.R
import com.mustafacandasdemir.candasdemir_hw2.model.Recipe

class CustomRecyclerViewAdapter2(private val recipes: List<Recipe>) : RecyclerView.Adapter<CustomRecyclerViewAdapter2.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item2_layout, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]

        // Tarif bilgilerini set et
        holder.tvItemRecipeName.text = recipe.name
        holder.tvItemIngredientsName.text = recipe.ingredients

        // Detay butonuna tıklama
        holder.btnItemDetail.setOnClickListener {
            showRecipeDetailDialog(holder.itemView, recipe)
        }
    }

    override fun getItemCount(): Int = recipes.size

    // ViewHolder sınıfı
    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItemRecipeName: TextView = itemView.findViewById(R.id.tvItemRecipeName)
        val tvItemIngredientsName: TextView = itemView.findViewById(R.id.tvItemIngredientsName)
        val btnItemDetail: Button = itemView.findViewById(R.id.btnItemDetail)
    }

    /**
     * Function to show a dialog with recipe details
     */
    private fun showRecipeDetailDialog(view: View, recipe: Recipe) {
        val dialog = Dialog(view.context)
        dialog.setContentView(R.layout.dialog) // Custom dialog layout

        // Dialog'daki view'leri referans al
        val tvDialogRecipeName: TextView = dialog.findViewById(R.id.tvDialogRecipeName)
        val tvDialogIngredients: TextView = dialog.findViewById(R.id.tvDialogIngredients)
        val tvDialogDescription: TextView = dialog.findViewById(R.id.tvDialogDescription)
        val btnCloseDialog: Button = dialog.findViewById(R.id.btnCloseDialog)

        // Dialog'daki metinleri set et
        tvDialogRecipeName.text = recipe.name
        tvDialogIngredients.text = "Ingredients: ${recipe.ingredients}"
        tvDialogDescription.text = "Description: ${recipe.details}"

        // Kapat butonuna tıklanınca dialog'u kapat
        btnCloseDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
