package com.example.bitfitPart2

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

private const val TAG = "FoodListFragment"

class FoodListFragment(application: Application) : Fragment() {

    val foods = mutableListOf<DisplayFood>()
    private lateinit var foodsRecyclerView: RecyclerView
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var application: Application

    init {
        this@FoodListFragment.application = application
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_food_list, container, false)

        // Add these configurations for the recyclerView and to configure the adapter
        val layoutManager = LinearLayoutManager(context)
        foodsRecyclerView = view.findViewById(R.id.food_recycler_view)
        foodsRecyclerView.layoutManager = layoutManager
        foodsRecyclerView.setHasFixedSize(true)
        foodAdapter = FoodAdapter(view.context, foods)
        foodsRecyclerView.adapter = foodAdapter

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

        // Update the return statement to return the inflated view from above
        return view
    }

    companion object {
        fun newInstance(application: Application): FoodListFragment {
            return FoodListFragment(application)
        }
    }
}