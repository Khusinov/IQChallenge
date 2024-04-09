package uz.khusinov.iqchallenge.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.khusinov.iqchallenge.R
import uz.khusinov.iqchallenge.databinding.FragmentHomeBinding
import uz.khusinov.iqchallenge.utills.Pref
import uz.khusinov.iqchallenge.utills.viewBinding

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
    }
}