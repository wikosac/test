package org.d3ifcool.gasdect.ui.riwayat

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.d3ifcool.gasdect.api.ApiConfig
import org.d3ifcool.gasdect.databinding.ActivityRiwayatBinding
import org.d3ifcool.gasdect.model.ResponseRiwayat
import org.d3ifcool.gasdect.model.Riwayat
import org.d3ifcool.gasdect.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRiwayatBinding
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RiwayatAdapter
    private var historiList: MutableList<Riwayat> = ArrayList()

    companion object {
        private const val TAG = "HistoriFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView

        recyclerAdapter = RiwayatAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter

        getDataFromAPI()
    }


    private fun getDataFromAPI(){
        val apiInterface = ApiConfig.create().getDataGas(MainActivity.token)

        apiInterface.enqueue(object : Callback<ResponseRiwayat> {
            override fun onResponse(call: Call<ResponseRiwayat>, response: Response<ResponseRiwayat>) {
                if(response.body()?.result?.isEmpty() == true) { // if data null
                    binding.emptyView.visibility = View.VISIBLE
                } else {
                    binding.emptyView.visibility = View.GONE

                    historiList = (response.body()?.result ?: ArrayList()) as MutableList<Riwayat>
                    Log.d("TAG", "Response = $historiList")
                    recyclerAdapter.setHistoriList(this@RiwayatActivity, historiList)
                }
            }

            override fun onFailure(call: Call<ResponseRiwayat>, t: Throwable) {
                Log.e(TAG, "Failed to load data from API", t)
                val snackbar =
                    Snackbar.make(
                        this@RiwayatActivity.findViewById(android.R.id.content),
                        "Gagal Menampilkan Data",
                        Snackbar.LENGTH_SHORT
                    )
                snackbar.show()
            }
        })
    }
}