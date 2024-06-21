package com.example.sajiindong.ui.diet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sajiindong.R
import com.example.sajiindong.adapter.RecommendationAdapter
import com.example.sajiindong.model.BeverageItem
import com.example.sajiindong.model.FoodItem
import com.example.sajiindong.model.Recommendation
import org.json.JSONArray
import org.json.JSONObject
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

@Suppress("DEPRECATION")
class DietActivity : AppCompatActivity() {

    private lateinit var interpreter: Interpreter
    private lateinit var foodItems: List<FoodItem>
    private lateinit var beverageItems: List<BeverageItem>
    private lateinit var recommendationList: MutableList<Recommendation>
    private lateinit var recommendationAdapter: RecommendationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet)

        // Load TensorFlow Lite model
        interpreter = Interpreter(FileUtil.loadMappedFile(this, "Sajiin_Dong_model.tflite"))

        // Load food and beverage data from JSON files
        foodItems = loadFoodItemsFromJson("food.json")
        beverageItems = loadBeverageItemsFromJson("beverage.json")

        // Setup RecyclerView
        recommendationList = mutableListOf()
        recommendationAdapter = RecommendationAdapter(recommendationList)
        val rvRecommendations = findViewById<RecyclerView>(R.id.rv_recommendations)
        rvRecommendations.layoutManager = LinearLayoutManager(this)
        rvRecommendations.adapter = recommendationAdapter

        val btnGetRecommendations = findViewById<Button>(R.id.btn_get_recommendations)
        val etGoalWeightLoss = findViewById<EditText>(R.id.et_goal_weight_loss)
        val etCurrentWeight = findViewById<EditText>(R.id.et_current_weight)
        val etDaysToGoal = findViewById<EditText>(R.id.et_days_to_goal)
        val spinnerCategory = findViewById<Spinner>(R.id.spinner_category)

        // Setup Spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.category_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategory.adapter = adapter
        }

        btnGetRecommendations.setOnClickListener {
            val goalWeightLoss = etGoalWeightLoss.text.toString().toFloatOrNull()
            val currentWeight = etCurrentWeight.text.toString().toFloatOrNull()
            val daysToGoal = etDaysToGoal.text.toString().toFloatOrNull()
            val selectedCategory = spinnerCategory.selectedItem.toString()

            if (goalWeightLoss == null || currentWeight == null || daysToGoal == null) {
                // Handle invalid input
                return@setOnClickListener
            }

            // Prepare input tensor
            val inputBuffer = prepareInput(goalWeightLoss, currentWeight, daysToGoal)

            // Prepare output tensor
            val outputShape = interpreter.getOutputTensor(0).shape()
            val outputBuffer = TensorBuffer.createFixedSize(outputShape, DataType.FLOAT32)

            // Run inference
            interpreter.run(inputBuffer, outputBuffer.buffer)

            // Process output
            val recommendationScore = outputBuffer.floatArray[0]
            displayRecommendations(recommendationScore, selectedCategory)
        }

        // Setup back button in ActionBar
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)         }
    }

    private fun prepareInput(goalWeightLoss: Float, currentWeight: Float, daysToGoal: Float): ByteBuffer {
        val inputBuffer = ByteBuffer.allocateDirect(6 * 4).order(ByteOrder.nativeOrder())

        inputBuffer.putFloat(goalWeightLoss)
        inputBuffer.putFloat(currentWeight)
        inputBuffer.putFloat(daysToGoal)

        return inputBuffer
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun displayRecommendations(score: Float, category: String) {
        val filteredRecommendations = mutableListOf<Recommendation>()
        val nameSet = mutableSetOf<String>() // Set to store unique names

        // Example logic: Recommend food items if score is high, otherwise recommend beverages
        val threshold = 0.5 // Adjust the threshold as necessary
        when (category) {
            "Diet" -> {
                if (score >= threshold) {
                    foodItems.filter { it.category == "Diet" }.forEach {
                        if (!nameSet.contains(it.name)) {
                            filteredRecommendations.add(Recommendation(it.name, it.calories, it.amountForDiet))
                            nameSet.add(it.name)
                        }
                    }
                } else {
                    beverageItems.filter { it.category == "Diet" }.forEach {
                        if (!nameSet.contains(it.name)) {
                            filteredRecommendations.add(Recommendation(it.name, it.calories, it.amountForDiet))
                            nameSet.add(it.name)
                        }
                    }
                }
            }
            "Bulking" -> {
                if (score >= threshold) {
                    foodItems.filter { it.category == "Bulking" }.forEach {
                        if (!nameSet.contains(it.name)) {
                            filteredRecommendations.add(Recommendation(it.name, it.calories, it.amountForBulking))
                            nameSet.add(it.name)
                        }
                    }
                } else {
                    beverageItems.filter { it.category == "Bulking" }.forEach {
                        if (!nameSet.contains(it.name)) {
                            filteredRecommendations.add(Recommendation(it.name, it.calories, it.amountForBulking))
                            nameSet.add(it.name)
                        }
                    }
                }
            }
        }

        // Update RecyclerView with filtered recommendations
        recommendationList.clear()
        recommendationList.addAll(filteredRecommendations)
        recommendationAdapter.notifyDataSetChanged()
    }

    private fun loadFoodItemsFromJson(fileName: String): List<FoodItem> {
        val jsonFileString = assets.open(fileName).bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonFileString)
        val items = mutableListOf<FoodItem>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject: JSONObject = jsonArray.getJSONObject(i)

            val number = jsonObject.optInt("Number", 0)
            val name = jsonObject.optString("Food Name", "Unknown Food")
            val ingredients = jsonObject.optString("Ingredients", "")
            val spices = jsonObject.optString("Spices", "")
            val foodType = jsonObject.optString("Food Type", "")
            val category = jsonObject.optString("Category", "")
            val amountForDiet = jsonObject.optString("Amount for Diet (g/mL)", "")
            val amountForBulking = jsonObject.optString("Amount for Bulking (g/mL)", "")
            val calories = jsonObject.optInt("Calories (kcal)", 0)
            val protein = jsonObject.optInt("Protein (g)", 0)
            val carbohydrates = jsonObject.optInt("Carbohydrates (g)", 0)
            val fats = jsonObject.optInt("Fats (g)", 0)

            val foodItem = FoodItem(number, name, ingredients, spices, foodType, category, amountForDiet, amountForBulking, calories, protein, carbohydrates, fats)
            items.add(foodItem)
        }

        return items
    }

    private fun loadBeverageItemsFromJson(fileName: String): List<BeverageItem> {
        val jsonFileString = assets.open(fileName).bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonFileString)
        val items = mutableListOf<BeverageItem>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject: JSONObject = jsonArray.getJSONObject(i)

            val number = jsonObject.optInt("Number", 0)
            val name = jsonObject.optString("Beverage Name", "Unknown Beverage")
            val ingredients = jsonObject.optString("Ingredients", "")
            val additives = jsonObject.optString("Additives", "")
            val category = jsonObject.optString("Category", "")
            val amountForDiet = jsonObject.optString("Amount for Diet (mL)", "")
            val amountForBulking = jsonObject.optString("Amount for Bulking (mL)", "")
            val calories = jsonObject.optInt("Calories (kcal)", 0)
            val protein = jsonObject.optInt("Protein (g)", 0)
            val carbohydrates = jsonObject.optInt("Carbohydrates (g)", 0)
            val fats = jsonObject.optInt("Fats (g)", 0)

            val beverageItem = BeverageItem(number, name, ingredients, additives, category, amountForDiet, amountForBulking, calories, protein, carbohydrates, fats)
            items.add(beverageItem)
        }

        return items
    }

    override fun onDestroy() {
        interpreter.close()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
