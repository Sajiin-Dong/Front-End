package com.example.sajiindong.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sajiindong.R

@Suppress("DEPRECATION")
class DetailBulk : AppCompatActivity() {
    private var title: String = "Bulk Food Recommendation"

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_DIET = "extra_diet"
        const val EXTRA_DESCRIPTION = "extra_description"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_diet_food)

        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val tvSetName: TextView = findViewById(R.id.tv_item_name)
        val imgPhoto: ImageView = findViewById(R.id.img_item_photo)
        val tvDiet: TextView = findViewById(R.id.tv_item_diet)
        val tvDescription: TextView = findViewById(R.id.tv_item_description)
        val btnFavorite: Button = findViewById(R.id.btn_set_favorite)
        val btnShare: Button = findViewById(R.id.btn_set_share)

        val tName = intent.getStringExtra(EXTRA_NAME)
        val tImg = intent.getIntExtra(EXTRA_PHOTO, 0)
        val tDiet = intent.getStringExtra(EXTRA_DIET)
        val tDescription = intent.getStringExtra(EXTRA_DESCRIPTION)

        tvSetName.text = tName
        Glide.with(this)
            .load(tImg)
            .apply(RequestOptions())
            .into(imgPhoto)
        tvDiet.text = tDiet
        tvDescription.text = tDescription

        btnFavorite.setOnClickListener {
            Toast.makeText(
                btnFavorite.context,
                "Memberikan Favorit pada $tName",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "Ayo bagikan komposisi makanan $tName :\n$tDiet")
            startActivity(Intent.createChooser(intent, "Share with..."))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}