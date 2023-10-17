package com.example.bitfit


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val FOOD_EXTRA = "FOOD_EXTRA"
private const val TAG = "FoodAdapter"

class FoodAdapter(private val context: Context, private val foods: List<DisplayFood>) :
    RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foods[position]
        holder.bind(food)
    }

    override fun getItemCount() = foods.size

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val foodTextView = itemView.findViewById<TextView>(R.id.foodValue)
        private val caloriesTextView = itemView.findViewById<TextView>(R.id.caloriesValue)

        init {
            itemView.setOnClickListener(this)
        }

        // TODO: Write a helper method to help set up the onBindViewHolder method
        fun bind(food: DisplayFood) {
            foodTextView.text = food.name //?.main
            caloriesTextView.text = food.calories

        }

        override fun onClick(v: View?) {
        }
    }
}