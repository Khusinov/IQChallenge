package uz.khusinov.iqchallenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import uz.khusinov.iqchallenge.R
import uz.khusinov.iqchallenge.databinding.FragmentQuizBinding
import uz.khusinov.iqchallenge.utills.viewBinding

class QuizFragment : Fragment(R.layout.fragment_quiz) {
   private val binding by viewBinding { FragmentQuizBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() = with(binding){
        result.setOnClickListener {
            findNavController().navigate(R.id.action_quizFragment_to_resultFragment)
        }
    }
}