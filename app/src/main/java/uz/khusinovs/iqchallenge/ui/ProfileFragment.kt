package uz.khusinovs.iqchallenge.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import uz.khusinovs.iqchallenge.R
import uz.khusinovs.iqchallenge.databinding.FragmentProfileBinding
import uz.khusinovs.iqchallenge.utills.Pref
import uz.khusinovs.iqchallenge.utills.viewBinding


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding { FragmentProfileBinding.bind(it) }
    val db = Firebase.firestore


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {

            logout.setOnClickListener {
                Pref.isLogin = false
                Pref.name = ""
                Pref.id = ""
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }

            deleteAccount.setOnClickListener {
                Log.d("TAG", "setupUI: docid ${Pref.documentId} ")
                val documentReference = db.collection("user").document(Pref.documentId)

                documentReference.delete().addOnSuccessListener {

                    Log.d("TAG", "setupUI: deleted ")
                    Pref.isLogin = false
                    Pref.name = ""
                    Pref.id = ""

                    findNavController().navigate(R.id.action_profileFragment_to_loginFragment)

                }.addOnFailureListener {
                    Log.d("TAG", "setupUI: ")
                }
            }


        }
    }
}