package uz.khusinov.iqchallenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import uz.khusinov.iqchallenge.R
import uz.khusinov.iqchallenge.databinding.FragmentLoginBinding
import uz.khusinov.iqchallenge.utills.viewBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding by viewBinding { FragmentLoginBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() = with(binding){

        login.setOnClickListener {
            if (checkFields())
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun checkFields(): Boolean {
        return true
    }
}