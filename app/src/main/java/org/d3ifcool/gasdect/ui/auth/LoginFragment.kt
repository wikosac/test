package org.d3ifcool.gasdect.ui.auth

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.d3ifcool.gasdect.R
import org.d3ifcool.gasdect.databinding.FragmentLoginBinding
import org.d3ifcool.gasdect.ui.MainActivity

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: AuthViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val email = binding.edEmail.text
        val password = binding.edPassword.text

        binding.tvRegister.setOnClickListener { toReg() }
        binding.btnLogin.setOnClickListener {
            viewModel.login(email.toString(), password.toString(), requireActivity(), ::toMain)
        }
        return binding.root
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
}