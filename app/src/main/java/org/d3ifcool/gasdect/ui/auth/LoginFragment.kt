package org.d3ifcool.gasdect.ui.auth

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.d3ifcool.gasdect.R
import org.d3ifcool.gasdect.SettingPreferences
import org.d3ifcool.gasdect.ViewModelFactory
import org.d3ifcool.gasdect.databinding.FragmentLoginBinding
import org.d3ifcool.gasdect.ui.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login")
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        viewModel = ViewModelProvider(this, ViewModelFactory(pref))[AuthViewModel::class.java]

        viewModel.getTokenPref().observe(viewLifecycleOwner) {
            if (it != null) moveTo(MainActivity::class.java) else moveTo(AuthActivity::class.java)
            Log.d("testo", "splash: $it")
        }

        val email = binding.edEmail.text
        val password = binding.edPassword.text
        binding.tvRegister.setOnClickListener { toReg() }
        binding.btnLogin.setOnClickListener {
            viewModel.login(email.toString(), password.toString(), requireActivity(), ::toMain)
            viewModel.response.observe(viewLifecycleOwner) {
                if (it != null) viewModel.saveTokenPref(email.toString())
            }
        }
    }

    private fun toReg() {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragmentRegister = RegisterFragment()
        fragmentTransaction.apply {
            replace(R.id.container, fragmentRegister)
            addToBackStack(null)
            commit()
        }
    }

    private fun toMain() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
    }

    private fun moveTo(page: Class<*>) {
        val intent = Intent(requireActivity(), page)
        startActivity(intent)
        requireActivity().finish()
    }

}