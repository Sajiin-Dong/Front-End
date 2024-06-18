package com.example.sajiindong.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sajiindong.R
import com.example.sajiindong.adapter.DietFoodAdapter
import com.example.sajiindong.data.FoodsData
import com.example.sajiindong.model.Foods
import com.example.sajiindong.ui.diet.DietActivity

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private lateinit var rvDiets: RecyclerView
    private var list: ArrayList<Foods> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            // Tambahkan data hanya jika instance state belum disimpan (baru pertama kali dibuat)
            list.addAll(FoodsData.listData)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        rvDiets = view.findViewById(R.id.rv_diet)
        rvDiets.setHasFixedSize(true)

        showRecyclerList(view)

        val btnDiet: Button = view.findViewById(R.id.btn_diet)
        btnDiet.setOnClickListener {
            val intent = Intent(activity, DietActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun showRecyclerList(view: View) {
        rvDiets.layoutManager = LinearLayoutManager(view.context)
        val listTeamAdapter = DietFoodAdapter(list)
        rvDiets.adapter = listTeamAdapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }
}
