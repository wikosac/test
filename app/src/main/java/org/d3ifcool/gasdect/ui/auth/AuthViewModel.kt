package org.d3ifcool.gasdect.ui.auth

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import org.d3ifcool.gasdect.SettingPreferences
import org.d3ifcool.gasdect.Util

class AuthViewModel(private val pref: SettingPreferences) : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val _response = MutableLiveData<FirebaseUser>()
    val response: LiveData<FirebaseUser> = _response

    fun register(email: String, password: String, context: Context, function: () -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(Activity()) { task ->
                if (task.isSuccessful) {
                    _response.value = firebaseAuth.currentUser
                    Util.showToast(context, "Berhasil register: ${_response.value}")
                    Log.d("testo", "login: ${_response.value}")
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

    fun getTokenPref(): LiveData<String?> = pref.getToken().asLiveData()

    fun saveTokenPref(token: String) {
        viewModelScope.launch {
            pref.saveToken(token)
        }
    }
}