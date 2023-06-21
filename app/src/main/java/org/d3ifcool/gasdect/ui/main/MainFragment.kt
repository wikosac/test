package org.d3ifcool.gasdect.ui

import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import org.d3ifcool.gasdect.R
import org.d3ifcool.gasdect.databinding.ActivityMainBinding
import org.d3ifcool.gasdect.notify.NoificationUtils.sendNotification
import org.d3ifcool.gasdect.ui.auth.AuthActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var user: FirebaseAuth
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance()
        viewModel.isConnected()
        viewModel.boolValue.observe(this) {
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
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                viewModel.getIntValue()
                Handler(Looper.getMainLooper()).post {
                    viewModel.intValue.observe(this@MainActivity) {
                        binding.tvValue.text = it.toString()
                        binding.tvValue.setTextColor(
                            if (it > 400) Color.RED else Color.BLACK
                        )
                        if (it > 400) {
                            tampilNotifikasi()
                        }
                    }
                }
            }
        }, 0, 1000)
    }

    private fun tampilNotifikasi() {
        val notificationManager = ContextCompat.getSystemService(
            this, NotificationManager::class.java)
        notificationManager?.sendNotification(this)
    }

    private fun logout() {
        user.signOut()
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    private fun confirmLogout() {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Logout")
            setMessage("Are you sure you want to logout?")
            setPositiveButton("Yes") { _, _ -> logout() }
            setNegativeButton("No", null)
        }
        val dialog = builder.create()
        dialog.show()
    }

}