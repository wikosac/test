package org.d3ifcool.gasdect.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.d3ifcool.gasdect.R

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
    }
}