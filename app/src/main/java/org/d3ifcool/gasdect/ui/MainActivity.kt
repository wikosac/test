package org.d3ifcool.gasdect.ui

import android.app.NotificationManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import org.d3ifcool.gasdect.R
import org.d3ifcool.gasdect.databinding.ActivityMainBinding
import org.d3ifcool.gasdect.notify.NoificationUtils.sendNotification
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isConnected()
        viewModel.boolValue.observe(this) {
            if (it == true) {
                run()
            } else {
                binding.tvValue.text = getString(R.string.not_connected)
            }
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

}