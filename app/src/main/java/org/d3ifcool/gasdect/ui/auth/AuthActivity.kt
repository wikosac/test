package org.d3ifcool.gasdect.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import org.d3ifcool.gasdect.R
import org.d3ifcool.gasdect.ui.MainActivity

class AuthActivity : AppCompatActivity() {

    private lateinit var user: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
            this.finish()
        }
        user = FirebaseAuth.getInstance()
        checkUserIsLogged()
    }

    private fun checkUserIsLogged() {
        if (user.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
    }
}