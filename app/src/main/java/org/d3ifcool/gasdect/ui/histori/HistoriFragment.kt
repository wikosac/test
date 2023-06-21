package org.d3ifcool.gasdect.ui.histori

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.d3ifcool.gasdect.api.ApiConfig
import org.d3ifcool.gasdect.databinding.FragmentHistoriBinding
import org.d3ifcool.gasdect.model.Histori
import org.d3ifcool.gasdect.model.ResponseHistori
import org.d3ifcool.gasdect.ui.main.MainFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoriFragment : Fragment() {
    private lateinit var binding: FragmentHistoriBinding
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: HistoriAdapter
    private var historiList: MutableList<Histori> = ArrayList()

    companion object {
        private const val TAG = "HistoriFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoriBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerAdapter = HistoriAdapter(requireContext())
        recyclerView = binding.recyclerView

        recyclerAdapter = HistoriAdapter(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerAdapter

        getDataFromAPI()

    }

    private fun getDataFromAPI(){
        val apiInterface = ApiConfig.create().getDataGas(MainFragment.token)

        apiInterface.enqueue(object : Callback<ResponseHistori> {
            override fun onResponse(call: Call<ResponseHistori>, response: Response<ResponseHistori>) {
                if(response.body()?.result?.isEmpty() == true) { // if data null
                    binding.emptyView.visibility = View.VISIBLE
                } else {
                    binding.emptyView.visibility = View.GONE

                    historiList = (response.body()?.result ?: ArrayList()) as MutableList<Histori>
                    Log.d("TAG", "Response = $historiList")
                    recyclerAdapter.setHistoriList(requireContext(), historiList)
                }
            }

            override fun onFailure(call: Call<ResponseHistori>, t: Throwable) {
                Log.e(TAG, "Failed to load data from API", t)
                val snackbar =
                    Snackbar.make(
                        (context as Activity).findViewById(android.R.id.content),
                        "Gagal Menampilkan Data",
                        Snackbar.LENGTH_SHORT
                    )
                snackbar.show()
            }
        })
    }
}