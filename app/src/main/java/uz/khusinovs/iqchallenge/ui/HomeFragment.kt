package uz.khusinovs.iqchallenge.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.khusinovs.iqchallenge.R
import uz.khusinovs.iqchallenge.databinding.FragmentHomeBinding
import uz.khusinovs.iqchallenge.utills.Pref
import uz.khusinovs.iqchallenge.utills.viewBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding { FragmentHomeBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() = with(binding) {
        leaderBoard.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_leaderBoardFragment)
        }

        beginner.setOnClickListener {
            Pref.level = "1"
            findNavController().navigate(R.id.action_homeFragment_to_quizFragment)
        }

        middle.setOnClickListener {
            Pref.level = "2"
            findNavController().navigate(R.id.action_homeFragment_to_quizFragment)
        }

        pro.setOnClickListener {
            Pref.level = "3"
            findNavController().navigate(R.id.action_homeFragment_to_quizFragment)
        }

        logo.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }
}