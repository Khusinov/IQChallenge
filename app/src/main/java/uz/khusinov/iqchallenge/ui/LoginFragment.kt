package uz.khusinov.iqchallenge.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import uz.khusinov.iqchallenge.R
import uz.khusinov.iqchallenge.databinding.FragmentLoginBinding
import uz.khusinov.iqchallenge.models.User
import uz.khusinov.iqchallenge.utills.Pref
import uz.khusinov.iqchallenge.utills.viewBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding by viewBinding { FragmentLoginBinding.bind(it) }
    private var deviceId: String? = null
    private var username1: String? = null
    val db = Firebase.firestore


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        getDeviceId()
    }

    private fun setupUI() = with(binding) {

        login.setOnClickListener {
            if (checkFields()) {
                username1 = username.text.toString()
                deviceId = deviceId ?: "null"
            }

            val user = User(deviceId!!, username1!!)
            db.collection("user")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Pref.isLogin = true
                    Pref.name = username1!!
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }

            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

    }

    private fun checkFields(): Boolean {
        return binding.login.text.isNotEmpty()
    }

    @SuppressLint("HardwareIds")
    private fun getDeviceId() {
        deviceId =
            Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)
    }
}