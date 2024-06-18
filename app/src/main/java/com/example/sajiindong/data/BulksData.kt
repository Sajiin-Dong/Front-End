package com.example.sajiindong.data

import com.example.sajiindong.R
import com.example.sajiindong.model.Foods

object BulksData {
    private val foodNames = arrayOf(
        "Steak Daging Sapi",
        "Nasi Merah",
        "Smoothie Protein",
        "Kacang Tanah",
        "Ubi Jalar",
        "Tuna",
        "Daging Sapi Giling",
        "Pasta Gandum Utuh",
        "Kale",
        "Omelet",
        "Lentil",
        "Edamame"
    )

    private val foodDiets = arrayOf(
        "Kalori: 679 kkal\nKarbohidrat: 0g\nProtein: 62g\nLemak: 48g",
        "Kalori: 218 kkal\nKarbohidrat: 45g\nProtein: 5g\nLemak: 1.6g",
        "Kalori: 350 kkal\nKarbohidrat: 50g\nProtein: 25g\nLemak: 5g",
        "Kalori: 567 kkal\nKarbohidrat: 16g\nProtein: 25g\nLemak: 49g",
        "Kalori: 103 kkal\nKarbohidrat: 24g\nProtein: 2g\nLemak: 0g",
        "Kalori: 132 kkal\nKarbohidrat: 0g\nProtein: 29g\nLemak: 1g",
        "Kalori: 332 kkal\nKarbohidrat: 0g\nProtein: 30g\nLemak: 22g",
        "Kalori: 174 kkal\nKarbohidrat: 37g\nProtein: 7g\nLemak: 1.5g",
        "Kalori: 35 kkal\nKarbohidrat: 7g\nProtein: 2.5g\nLemak: 0.5g",
        "Kalori: 154 kkal\nKarbohidrat: 1.3g\nProtein: 13g\nLemak: 10g",
        "Kalori: 116 kkal\nKarbohidrat: 20g\nProtein: 9g\nLemak: 0.4g",
        "Kalori: 122 kkal\nKarbohidrat: 9g\nProtein: 11g\nLemak: 5g"
    )

    private val foodDescriptions = arrayOf(
        "Steak daging sapi adalah sumber protein dan lemak yang sangat baik untuk pertumbuhan otot. Daging sapi juga kaya akan zat besi dan vitamin B.",
        "Nasi merah adalah sumber karbohidrat kompleks yang baik, menyediakan energi tahan lama untuk aktivitas fisik dan pemulihan.",
        "Smoothie protein adalah minuman yang terbuat dari campuran protein powder, buah, dan susu atau air. Cocok untuk meningkatkan asupan protein harian.",
        "Kacang tanah adalah sumber protein nabati dan lemak sehat. Sangat baik untuk menambah kalori dalam diet bulking.",
        "Ubi jalar adalah sumber karbohidrat kompleks dan serat yang baik. Juga kaya akan vitamin A dan C.",
        "Tuna adalah ikan yang kaya protein dan rendah lemak. Juga mengandung omega-3 yang baik untuk kesehatan jantung.",
        "Daging sapi giling adalah pilihan protein hewani yang baik dengan kandungan lemak yang bervariasi tergantung pada potongannya.",
        "Pasta gandum utuh adalah sumber karbohidrat kompleks yang baik, ideal untuk mengisi bahan bakar tubuh sebelum latihan berat.",
        "Kale adalah sayuran hijau yang kaya akan vitamin K, C, dan A. Juga mengandung antioksidan yang baik untuk kesehatan.",
        "Omelet adalah telur yang dikocok dan digoreng, seringkali dengan tambahan sayuran atau daging. Sumber protein berkualitas tinggi.",
        "Lentil adalah kacang-kacangan yang kaya akan protein dan serat. Baik untuk menambah asupan protein nabati.",
        "Edamame adalah kacang kedelai muda yang kaya protein dan serat. Juga mengandung berbagai vitamin dan mineral penting."
    )

    private val foodImages = intArrayOf(
        R.drawable.steak_daging_sapi,
        R.drawable.nasi_merah,
        R.drawable.smoothie_protein,
        R.drawable.kacang_tanah,
        R.drawable.ubi_jalar,
        R.drawable.tuna,
        R.drawable.daging_sapi_giling,
        R.drawable.pasta_gandum_utuh,
        R.drawable.kale,
        R.drawable.omelet,
        R.drawable.lentil,
        R.drawable.edamame
    )

    val listData: ArrayList<Foods>
        get() {
            val list = arrayListOf<Foods>()
            for (position in foodNames.indices) {
                val foods = Foods()
                foods.name = foodNames[position]
                foods.photo = foodImages[position]
                foods.diet = foodDiets[position]
                foods.description = foodDescriptions[position]
                list.add(foods)
            }
            return list
        }
}
