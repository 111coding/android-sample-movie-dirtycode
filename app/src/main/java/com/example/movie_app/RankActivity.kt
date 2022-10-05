package com.example.movie_app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RankActivity : AppCompatActivity() {

    var list : List<MovieDto>? = null

    private var recyclerView: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: ResultRecyclerViewAdpater = ResultRecyclerViewAdpater()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank)

        val bundle = intent.extras
        list = bundle?.getSerializable("movieList") as List<MovieDto>
        Log.d("My","RankActivity의 MovieList : $list")

        recyclerView = findViewById(R.id.rv_movie)
        recyclerView!!.adapter = ResultRecyclerViewAdpater()
        recyclerView!!.layoutManager = LinearLayoutManager(applicationContext)

    }


    inner class ResultRecyclerViewAdpater :
        RecyclerView.Adapter<ResultRecyclerViewAdpater.ResultViewHolder>() {


        override fun onCreateViewHolder(


            parent: ViewGroup,
            viewType: Int
        ): ResultRecyclerViewAdpater.ResultViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
            return ResultViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list!!.size
        }

        override fun onBindViewHolder(
            holder: ResultRecyclerViewAdpater.ResultViewHolder,
            position: Int
        ) {
            holder.apply {
                rankTextView.text = list!![position].rank
                rankIntenTextView.text = list!![position].rankInten
                movieNameTextView.text = list!![position].movieNm
            }
        }

        inner class ResultViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            //위의 onCreateViewHolder에서 생성된 view를 가지고 실행함
            val rankTextView: TextView = view.findViewById(R.id.rank)
            val movieNameTextView: TextView = view.findViewById(R.id.movie_name)
            val rankIntenTextView: TextView = view.findViewById(R.id.count1)
        }
    }
}