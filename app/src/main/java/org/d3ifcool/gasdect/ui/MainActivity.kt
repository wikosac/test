package org.d3ifcool.gasdect.ui

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import org.d3ifcool.gasdect.R
import org.d3ifcool.gasdect.SettingPreferences
import org.d3ifcool.gasdect.ViewModelFactory
import org.d3ifcool.gasdect.databinding.ActivityMainBinding
import org.d3ifcool.gasdect.notify.NoificationUtils.sendNotification
import org.d3ifcool.gasdect.ui.auth.AuthActivity
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        viewModel = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]

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

    private fun logout() {
        viewModel.deleteTokenPref()
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                confirmLogout()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}