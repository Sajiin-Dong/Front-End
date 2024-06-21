package com.example.sajiindong.model

data class Beverage(
    val id: Int,
    val beverageName: String,
    val ingredients: String,
    val additives: String,
    val category: String,
    val amountDiet: String,
    val amountBulking: String,
    val calories: Int,
    val protein: Int,
    val carbohydrates: Int,
    val fats: Int
)

