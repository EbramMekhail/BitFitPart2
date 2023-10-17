package com.example.bitfit


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

private const val TAG = "MainActivity/"

class MainActivity : AppCompatActivity() {
    private lateinit var foodsRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private val foods = mutableListOf<DisplayFood>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        foodsRecyclerView = findViewById(R.id.foods)
        val foodAdapter = FoodAdapter(this, foods)

        val addNewFoodBtn = findViewById<Button>(R.id.addFoodBtn)

        addNewFoodBtn.setOnClickListener {
            foodAdapter.notifyDataSetChanged()
            val intent = Intent(this, DetailActivity::class.java)
            this.startActivity(intent)
            Log.d(TAG, foods.toString())
        }

        lifecycleScope.launch {
            (application as FoodApplication).db.foodDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayFood(
                        entity.name,
                        entity.calories
                    )
                }.also { mappedList ->
                    foods.clear()
                    foods.addAll(mappedList)
                    foodAdapter.notifyDataSetChanged()
                }
            }
        }

        foodsRecyclerView.adapter = foodAdapter

        foodsRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, 0)
            foodsRecyclerView.addItemDecoration(dividerItemDecoration)
        }
    }
}