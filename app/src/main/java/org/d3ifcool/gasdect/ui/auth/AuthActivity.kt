package org.d3ifcool.gasdect.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.d3ifcool.gasdect.R
import org.d3ifcool.gasdect.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}