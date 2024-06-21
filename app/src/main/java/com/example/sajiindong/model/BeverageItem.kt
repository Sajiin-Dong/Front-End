package com.example.sajiindong.model

data class BeverageItem(
    val number: Int,
    val name: String,
    val ingredients: String,
    val additives: String,
    val category: String,
    val amountForDiet: String,
    val amountForBulking: String,
    val calories: Int,
    val protein: Int,
    val carbohydrates: Int,
    val fats: Int
)
