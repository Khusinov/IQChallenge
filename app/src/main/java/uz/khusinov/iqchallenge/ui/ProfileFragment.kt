package uz.khusinov.iqchallenge.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import uz.khusinov.iqchallenge.R
import uz.khusinov.iqchallenge.databinding.FragmentProfileBinding
import uz.khusinov.iqchallenge.utills.Pref
import uz.khusinov.iqchallenge.utills.viewBinding


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
                requireActivity().finish()
            }

            deleteAccount.setOnClickListener {
                Log.d("TAG", "setupUI: docid ${Pref.documentId} ")
                val documentReference = db.collection("user").document(Pref.documentId)

                documentReference.delete().addOnSuccessListener {

                    Log.d("TAG", "setupUI: deleted ")
                    Pref.isLogin = false
                    Pref.name = ""
                    Pref.id = ""
                    requireActivity().finish()

                }.addOnFailureListener {
                    Log.d("TAG", "setupUI: ")
                }
            }


        }
    }
}