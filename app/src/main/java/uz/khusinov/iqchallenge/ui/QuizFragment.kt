package uz.khusinov.iqchallenge.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.robinhood.ticker.TickerView
import kotlinx.coroutines.delay
import uz.khusinov.iqchallenge.R
import uz.khusinov.iqchallenge.databinding.FragmentQuizBinding
import uz.khusinov.iqchallenge.models.Quiz
import uz.khusinov.iqchallenge.utills.viewBinding
import java.text.SimpleDateFormat
import kotlin.math.abs

class QuizFragment : Fragment(R.layout.fragment_quiz) {
    private val binding by viewBinding { FragmentQuizBinding.bind(it) }
    private lateinit var timer: CountDownTimer
    private val quizzes = mutableListOf<Quiz>()
    private var currentQuestion = 1

    val db = Firebase.firestore


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        startTimer()
        setupUI()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(50000, 1000) {
            override fun onTick(p1: Long) {
                val p0 = p1 / 1000
               binding.ticker.text =
                    String.format(
                        "%02d:%02d",
                        (p0 - (((p0 / 60) / 60) / 24) * 24 * 60 * 60 - (((p0 / 60) / 60) % 24) * 60 * 60) / 60,
                        p0 % 60
                    )
            }

            override fun onFinish() {
                findNavController().navigate(R.id.action_quizFragment_to_resultFragment)
            }
        }.start()
    }

    private fun setupUI() = with(binding) {
        finish.setOnClickListener {
            findNavController().navigate(R.id.action_quizFragment_to_resultFragment)
        }

        answerA.setOnClickListener {
            rightGroup.clearCheck()
        }

        answerC.setOnClickListener {
            rightGroup.clearCheck()
        }

        answerE.setOnClickListener {
            rightGroup.clearCheck()
        }

        answerB.setOnClickListener {
            leftGroup.clearCheck()
        }

        answerD.setOnClickListener {
            leftGroup.clearCheck()
        }

        answerF.setOnClickListener {
            leftGroup.clearCheck()
        }
    }


    private fun loadData() {


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

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() = with(binding){
        val data = quizzes[currentQuestion-1]
        questionCount.text = "$currentQuestion/${quizzes.size}"
        question.text = data.question
        answerA.text = data.answer1

    }

    private fun clearAnswer() {

    }

    private fun setAnswer() {

    }
}