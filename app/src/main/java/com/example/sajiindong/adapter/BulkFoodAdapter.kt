package com.example.sajiindong.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sajiindong.R
import com.example.sajiindong.model.Foods
import com.example.sajiindong.ui.detail.DetailBulk
import com.example.sajiindong.ui.detail.DetailDiet

class BulkFoodAdapter(private val listFood: ArrayList<Foods>) : RecyclerView.Adapter<BulkFoodAdapter.ListViewHolder>() {

        inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
            var tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
            var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
            val view: View =
                LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.item_diet_food, viewGroup, false)
            return ListViewHolder(view)
        }

        override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
            val food = listFood[position]

            Glide.with(holder.itemView.context)
                .load(food.photo)
                .apply(RequestOptions())
                .into(holder.imgPhoto)

            holder.tvName.text = food.name
            holder.tvDescription.text = food.description

            holder.itemView.setOnClickListener {
                val context = holder.itemView.context
                val moveDetailBulk = Intent(context, DetailBulk::class.java)
                moveDetailBulk.putExtra(DetailDiet.EXTRA_NAME, food.name)
                moveDetailBulk.putExtra(DetailDiet.EXTRA_PHOTO, food.photo)
                moveDetailBulk.putExtra(DetailDiet.EXTRA_DIET, food.diet)
                moveDetailBulk.putExtra(DetailDiet.EXTRA_DESCRIPTION, food.description)
                context.startActivity(moveDetailBulk)
            }
        }

        override fun getItemCount(): Int {
            return listFood.size
        }
}