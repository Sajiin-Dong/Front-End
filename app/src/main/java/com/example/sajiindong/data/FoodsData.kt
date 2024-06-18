package com.example.sajiindong.data

import com.example.sajiindong.R
import com.example.sajiindong.model.Foods

object FoodsData {
    private val foodNames = arrayOf(
        "Salad Buah",
        "Oatmeal",
        "Yoghurt Rendah Lemak",
        "Almond",
        "Avokad",
        "Ikan Salmon",
        "Dada Ayam",
        "Quinoa",
        "Broccoli",
        "Telur Rebus",
        "Kacang Merah",
        "Bayam"
    )

    private val foodDiets = arrayOf(
        "Kalori\t: 150 kkal\nKarbohidrat\t: 25g\nProtein\t: 2g\nLemak\t: 4g",
        "Kalori\t: 150 kkal\nKarbohidrat\t: 27g\nProtein\t: 5g\nLemak\t: 3g",
        "Kalori\t: 100 kkal\nKarbohidrat\t: 12g\nProtein\t: 5g\nLemak\t: 2g",
        "Kalori\t: 160 kkal\nKarbohidrat\t: 6g\nProtein\t: 6g\nLemak\t: 14g",
        "Kalori\t: 234 kkal\nKarbohidrat\t: 12g\nProtein\t: 3g\nLemak\t: 21g",
        "Kalori\t: 206 kkal\nKarbohidrat\t: 0g\nProtein\t: 22g\nLemak\t: 12g",
        "Kalori\t: 165 kkal\nKarbohidrat\t: 0g\nProtein\t: 31g\nLemak\t: 4g",
        "Kalori\t: 120 kkal\nKarbohidrat\t: 21g\nProtein\t: 4g\nLemak\t: 2g",
        "Kalori\t: 55 kkal\nKarbohidrat\t: 11g\nProtein\t: 3.7g\nLemak\t: 0.6g",
        "Kalori\t: 77 kkal\nKarbohidrat\t: 0.6g\nProtein\t: 6.3g\nLemak\t: 5.3g",
        "Kalori\t: 113 kkal\nKarbohidrat\t: 20g\nProtein\t: 8g\nLemak\t: 0.5g",
        "Kalori\t: 23 kkal\nKarbohidrat\t: 3.6g\nProtein\t: 2.9g\nLemak\t: 0.4g"
    )

    private val foodDescriptions = arrayOf(
        "Salad buah adalah hidangan yang terdiri dari berbagai macam buah-buahan segar yang dicampur bersama. Biasanya disajikan dengan tambahan yoghurt, madu, atau sirup sebagai pemanis alami. Salad buah kaya akan vitamin dan mineral, serta serat yang baik untuk pencernaan.",
        "Oatmeal adalah makanan yang terbuat dari gandum utuh yang direbus dengan air atau susu. Oatmeal merupakan sumber serat larut yang baik, yang dapat membantu menurunkan kolesterol dan menjaga kesehatan jantung. Selain itu, oatmeal juga mengandung berbagai vitamin dan mineral penting.",
        "Yoghurt rendah lemak adalah produk olahan susu yang difermentasi dengan bakteri baik. Yoghurt ini rendah kalori dan lemak, tetapi kaya akan protein dan kalsium. Yoghurt rendah lemak dapat membantu meningkatkan kesehatan pencernaan dan memperkuat tulang.",
        "Almond adalah jenis kacang yang kaya akan lemak sehat, protein, dan serat. Almond juga mengandung vitamin E, magnesium, dan antioksidan yang dapat membantu melindungi tubuh dari stres oksidatif dan peradangan.",
        "Avokad adalah buah yang kaya akan lemak tak jenuh tunggal yang baik untuk kesehatan jantung. Selain itu, avokad juga mengandung serat, vitamin K, vitamin E, vitamin C, dan berbagai mineral penting. Konsumsi avokad secara teratur dapat membantu menurunkan kolesterol jahat dan meningkatkan kolesterol baik.",
        "Ikan salmon adalah sumber protein berkualitas tinggi yang juga kaya akan lemak omega-3. Lemak omega-3 sangat penting untuk kesehatan otak dan jantung. Salmon juga mengandung berbagai vitamin dan mineral, termasuk vitamin D dan selenium.",
        "Dada ayam adalah sumber protein tanpa lemak yang sangat baik. Dada ayam mengandung sedikit lemak dan kalori, tetapi tinggi protein, sehingga sangat baik untuk membangun dan memperbaiki otot. Dada ayam juga mengandung berbagai vitamin dan mineral penting.",
        "Quinoa adalah biji-bijian yang kaya akan protein, serat, dan berbagai vitamin dan mineral. Quinoa merupakan sumber protein lengkap yang mengandung semua asam amino esensial yang diperlukan tubuh. Selain itu, quinoa juga bebas gluten dan mudah dicerna.",
        "Broccoli adalah sayuran hijau yang kaya akan vitamin C, vitamin K, serat, dan antioksidan. Broccoli dapat membantu meningkatkan sistem kekebalan tubuh, menjaga kesehatan tulang, dan melindungi tubuh dari berbagai penyakit kronis.",
        "Telur rebus adalah sumber protein berkualitas tinggi yang juga mengandung berbagai vitamin dan mineral penting, termasuk vitamin B12, vitamin D, dan selenium. Telur rebus rendah kalori tetapi sangat mengenyangkan, sehingga baik untuk program penurunan berat badan.",
        "Kacang merah adalah sumber protein nabati yang baik, serta mengandung serat, zat besi, dan berbagai vitamin dan mineral. Kacang merah dapat membantu mengatur kadar gula darah dan meningkatkan kesehatan pencernaan.",
        "Bayam adalah sayuran hijau yang kaya akan vitamin A, vitamin C, vitamin K, zat besi, dan kalsium. Bayam rendah kalori tetapi tinggi nutrisi, sehingga sangat baik untuk menjaga kesehatan tubuh secara keseluruhan."
    )

    private val foodImages = intArrayOf(
        R.drawable.salad_buah,        // Salad Buah
        R.drawable.oatmeal,           // Oatmeal
        R.drawable.yoghurt_rendah_lemak, // Yoghurt Rendah Lemak
        R.drawable.almond,            // Almond
        R.drawable.avokad,            // Avokad
        R.drawable.ikan_salmon,       // Ikan Salmon
        R.drawable.dada_ayam,         // Dada Ayam
        R.drawable.quinoa,            // Quinoa
        R.drawable.broccoli,          // Broccoli
        R.drawable.telur_rebus,       // Telur Rebus
        R.drawable.kacang_merah,      // Kacang Merah
        R.drawable.bayam              // Bayam
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