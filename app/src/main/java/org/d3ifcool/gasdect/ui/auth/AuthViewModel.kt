package org.d3ifcool.gasdect.ui.auth

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import org.d3ifcool.gasdect.Util

class AuthViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun register(email: String, password: String, context: Context, function: () -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(Activity()) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    Util.showToast(context, "Berhasil register: $user")
                    Log.d("testo", "login: $user")
                    function()
                } else {
                    Util.showToast(context, "Gagal register: ${task.exception?.message}")
                    Log.e("testo", "login: ${task.exception?.message}")
                }
            }
    }

    fun login(email: String, password: String, context: Context, function: () -> Unit) {
        Log.d("testo", "$email $password")
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(Activity()) { task ->
                if (task.isSuccessful) {
                    // Login berhasil
                    val user = firebaseAuth.currentUser
                    // Lakukan tindakan yang sesuai setelah login berhasil
                    Util.showToast(context, "Berhasil login: $user")
                    Log.d("testo", "login: $user")
                    function()
                } else {
                    // Login gagal
                    Util.showToast(context, "Gagal login: ${task.exception?.message}")
                    Log.e("testo", "login: ${task.exception?.message}")
                }
            }
    }
}