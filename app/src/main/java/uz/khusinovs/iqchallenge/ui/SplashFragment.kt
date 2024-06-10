package uz.khusinovs.iqchallenge.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.khusinovs.iqchallenge.R
import uz.khusinovs.iqchallenge.utills.Pref

class SplashFragment : Fragment(R.layout.fragment_splash) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            delay(1500)
            if (Pref.isLogin)
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            else
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }
}