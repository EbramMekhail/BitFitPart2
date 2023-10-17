package com.example.bitfit

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

private const val TAG = "DetailActivity"

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {
    private lateinit var foodTextView: EditText
    private lateinit var caloriesTextView: EditText
    private lateinit var recordFoodBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        foodTextView = findViewById(R.id.foodInput)
        caloriesTextView = findViewById(R.id.caloriesInput)
        recordFoodBtn = findViewById(R.id.foodSubmitButton)

        recordFoodBtn.setOnClickListener {
            lifecycleScope.launch(IO) {
//                (application as FoodApplication).db.foodDao().deleteAll()
                (application as FoodApplication).db.foodDao().insert(
                    FoodEntity(
                        name = foodTextView.text.toString(),
                        calories = caloriesTextView.text.toString()
                    )
                )
            }
            this.finish()
        }
    }
}