package uz.khusinovs.iqchallenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import uz.khusinovs.iqchallenge.R
import uz.khusinovs.iqchallenge.databinding.FragmentRegisterBinding
import uz.khusinovs.iqchallenge.utills.viewBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private val binding by viewBinding { FragmentRegisterBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() = with(binding){

        login.setOnClickListener {
            if (checkFields())
                findNavController().navigateUp()
        }

        register.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
        }
    }

    private fun checkFields(): Boolean {
        return true
    }
}