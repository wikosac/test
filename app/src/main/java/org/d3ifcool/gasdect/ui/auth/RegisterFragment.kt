package org.d3ifcool.gasdect.ui.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.d3ifcool.gasdect.R
import org.d3ifcool.gasdect.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: AuthViewModel
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val email = binding.edEmailReg.text
        val password = binding.edPasswordReg.text

        binding.tvLogin.setOnClickListener { toLogin() }
        binding.btnRegister.setOnClickListener {
            viewModel.register(email.toString(), password.toString(), requireActivity(), ::toLogin)
        }
        return binding.root
    }

    private fun toLogin() {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragmentLogin = LoginFragment()
        fragmentTransaction.apply {
            replace(R.id.container, fragmentLogin)
            addToBackStack(null)
            commit()
        }
    }
}