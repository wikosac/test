package org.d3ifcool.gasdect.ui.main

import android.app.NotificationManager
import android.content.Intent
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
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import org.d3ifcool.gasdect.R
import org.d3ifcool.gasdect.databinding.FragmentMainBinding
import org.d3ifcool.gasdect.notify.NoificationUtils.sendNotification
import org.d3ifcool.gasdect.ui.MainViewModel
import org.d3ifcool.gasdect.ui.auth.LoginActivity
import java.util.*

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var user: FirebaseAuth
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
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
        viewModel.isConnected()
        viewModel.boolValue.observe(viewLifecycleOwner) {
            if (it == true) {
                run()
            } else {
                binding.tvValue.text = getString(R.string.not_connected)
            }
        }

        binding.logoutButton.setOnClickListener {
            confirmLogout()
        }
    }

    private fun run() {
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                viewModel.getIntValue()

                val fragmentView = view
                if (fragmentView != null) {
                    viewModel.intValue.observe(viewLifecycleOwner) { value ->
                        binding.tvValue.text = value.toString()
                        binding.tvValue.setTextColor(if (value > 400) Color.RED else Color.BLACK)
                        if (value > 400) {
                            tampilNotifikasi()
                        }
                    }
                }

                handler.postDelayed(this, 1000)
            }
        })
    }


    private fun tampilNotifikasi() {
        val notificationManager = ContextCompat.getSystemService(
            requireContext(), NotificationManager::class.java)
        notificationManager?.sendNotification(requireContext())
    }

    private fun logout() {
        user.signOut()
        startActivity(Intent(context, LoginActivity::class.java))
    }

    private fun confirmLogout() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle("Logout")
            setMessage("Are you sure you want to logout?")
            setPositiveButton("Yes") { _, _ -> logout() }
            setNegativeButton("No", null)
        }
        val dialog = builder.create()
        dialog.show()
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