package com.example.movie_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private val formatter = SimpleDateFormat("yyyyMM01", Locale.getDefault())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        val thisWeekBtn = findViewById<Button>(R.id.btn_this)
        val lastWeekBtn = findViewById<Button>(R.id.btn_last)
        thisWeekBtn.setOnClickListener {
            val now =  Calendar.getInstance().time;
            fetchDataAndMove(formatter.format(now));
        }

        lastWeekBtn.setOnClickListener {
            val cal = Calendar.getInstance().run {
                add(Calendar.MONTH, -1)
                time
            }
            fetchDataAndMove(formatter.format(cal));
        }





    }

    fun fetchDataAndMove(targetDt : String){
        Log.d("testest",targetDt)
        RetrofitBuilder.api
            .getMovieList(targetDt, "54f8e103cb76c1cb7a3c515727387d52")
            .enqueue(object : Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    //통신 실패한 경우
                    Log.d("testest", "${t.message}")
                    Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    val movieResponse = response.body()
                    val list : List<MovieDto> = movieResponse!!.boxofficeResult!!.dailyBoxOfficeList
                    val intent: Intent = Intent(this@MainActivity, RankActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable("movieList",(list as Serializable)) //list를 강제 형변환
                    intent.putExtras(bundle)
                    startActivity(intent)
                    Log.d("testest", "$list")
                }
            }
            )
    }
}
