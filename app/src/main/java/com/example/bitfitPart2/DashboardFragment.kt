package com.example.bitfitPart2

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragment(application: Application) : Fragment() {

    private lateinit var avgCalVal: TextView
    private lateinit var maxCalVal: TextView
    private lateinit var minCalVal: TextView
    private lateinit var clearDataBtn: Button
    private lateinit var application: Application

    init {
        this@DashboardFragment.application = application
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        avgCalVal = view.findViewById(R.id.avgCalVal)
        maxCalVal = view.findViewById(R.id.maxCalVal)
        minCalVal = view.findViewById(R.id.minCalVal)
        clearDataBtn = view.findViewById(R.id.clearDataBtn)

        clearDataBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                (application as FoodApplication).db.foodDao().deleteAll()
            }
            reset()
        }

        lifecycleScope.launch(Dispatchers.IO) {
            (application as FoodApplication).db.foodDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayFood(
                        entity.name,
                        entity.calories
                    )
                }.also { mappedList ->
                    update(mappedList)
                }
            }
        }
        return view
    }

    fun reset() {
        avgCalVal.text = "---"
        maxCalVal.text = "---"
        minCalVal.text = "---"
    }

    fun update(foods: List<DisplayFood>) {
        if (foods.isEmpty()) {
            reset()
            return
        }

        var sum: Int = 0
        var min: Int = Integer.MAX_VALUE
        var max: Int = Integer.MIN_VALUE
        for (food in foods) {
            val c = Integer.parseInt(food.calories)
            sum += c
            if (c < min) {
                min = c
            }
            if (c > max) {
                max = c
            }
        }
        avgCalVal.text = (sum / foods.size).toString()
        maxCalVal.text = max.toString()
        minCalVal.text = min.toString()
    }

    companion object {
        fun newInstance(application: Application): DashboardFragment {
            return DashboardFragment(application)
        }
    }
}