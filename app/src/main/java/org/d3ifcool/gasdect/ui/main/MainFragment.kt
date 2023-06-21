package org.d3ifcool.gasdect.ui.main

import android.app.NotificationManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import org.d3ifcool.gasdect.R
import org.d3ifcool.gasdect.databinding.FragmentMainBinding
import org.d3ifcool.gasdect.notify.NoificationUtils.sendNotification
import org.d3ifcool.gasdect.ui.MainViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var user: FirebaseAuth
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    companion object{
        const val token = "9PWWYxhkSuCnr4OD3VoKrfCPx0WsC4O7"
        const val TAG = "MainFragment"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        user = FirebaseAuth.getInstance()
        viewModel.isConnected(token)
        viewModel.boolValue.observe(viewLifecycleOwner) {
            if (it == true) {
                run()
            } else {
                binding.tvValue.text = getString(R.string.not_connected)
            }
        }
    }

    private fun run() {
        var isNotificationShown = false
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                viewModel.getIntValue()

                val fragmentView = view
                if (fragmentView != null) {
                    viewModel.intValue.observe(viewLifecycleOwner) { value ->
                        binding.tvValue.text = value.toString()
                        binding.tvValue.setTextColor(if (value > 400) Color.RED else Color.BLACK)
                        if (value > 400 && !isNotificationShown) {
                            tampilNotifikasi()
                            val currentDateTime = getCurrentDateTimeAsString()
                            viewModel.inputHistory(token, currentDateTime, value.toString())
                            isNotificationShown = true
                        }
                    }
                }

                handler.postDelayed(this, 1000)
            }
        })
    }

    fun getCurrentDateTimeAsString(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(calendar.time)
    }

    private fun tampilNotifikasi() {
        val notificationManager = ContextCompat.getSystemService(
            requireContext(), NotificationManager::class.java)
        notificationManager?.sendNotification(requireContext())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_histori) {
            findNavController().navigate(R.id.action_mainFragment_to_historiFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}