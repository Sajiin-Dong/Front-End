package com.example.sajiindong.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sajiindong.R
import com.example.sajiindong.adapter.BulkFoodAdapter
import com.example.sajiindong.data.BulksData
import com.example.sajiindong.model.Foods

@Suppress("DEPRECATION")
class DashboardFragment : Fragment() {

    private lateinit var rvBulkings: RecyclerView
    private var list: ArrayList<Foods> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        rvBulkings = view.findViewById(R.id.rv_bulking)
        rvBulkings.setHasFixedSize(true)

        list.addAll(BulksData.listData)
        showRecyclerList(view)

        return view
    }

    private fun showRecyclerList(view: View) {
        rvBulkings.layoutManager = LinearLayoutManager(view.context)
        val listTeamAdapter = BulkFoodAdapter(list)
        rvBulkings.adapter = listTeamAdapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }
}
