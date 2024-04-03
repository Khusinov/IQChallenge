package uz.khusinov.iqchallenge.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import uz.khusinov.iqchallenge.R
import uz.khusinov.iqchallenge.databinding.FragmentQuizBinding
import uz.khusinov.iqchallenge.models.Quiz
import uz.khusinov.iqchallenge.utills.viewBinding

class QuizFragment : Fragment(R.layout.fragment_quiz) {
    private val binding by viewBinding { FragmentQuizBinding.bind(it) }

    val db = Firebase.firestore


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        setupUI()
    }



    private fun setupUI() = with(binding) {
        finish.setOnClickListener {
            findNavController().navigate(R.id.action_quizFragment_to_resultFragment)
        }

        leftGroup.setOnCheckedChangeListener { group, checkedId ->
            rightGroup.clearCheck()
        }

        rightGroup.setOnCheckedChangeListener { group, checkedId ->
            leftGroup.clearCheck()
        }
    }

    private fun loadData() {
        val quizzes = mutableListOf<Quiz>()

        db.collection("quiz")
            .get()
            .addOnSuccessListener { result ->
                quizzes.clear()
                for (i in result) {
                    val quiz = Quiz(
                        i.data["id"].toString().toInt(),
                        i.data["question"].toString(),
                        i.data["questionImg"].toString(),
                        i.data["level"].toString().toInt(),
                        i.data["answer1"].toString(),
                        i.data["answer2"].toString(),
                        i.data["answer3"].toString(),
                        i.data["answer4"].toString(),
                        i.data["answer5"].toString(),
                        i.data["answer6"].toString(),
                        i.data["correctAnswer"].toString()
                    )
                    quizzes.add(quiz)
                }
                Log.d("TAG", "setupUI: tayyor ${quizzes.size} ")
            }

            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }
}