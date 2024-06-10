package uz.khusinovs.iqchallenge.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import uz.khusinovs.iqchallenge.R
import uz.khusinovs.iqchallenge.databinding.FragmentQuizBinding
import uz.khusinovs.iqchallenge.models.Quiz
import uz.khusinovs.iqchallenge.utills.Pref
import uz.khusinovs.iqchallenge.utills.viewBinding

class QuizFragment : Fragment(R.layout.fragment_quiz) {
    private val binding by viewBinding { FragmentQuizBinding.bind(it) }
    private lateinit var timer: CountDownTimer
    private val quizzes = mutableListOf<Quiz>()
    private var currentQuestion = 1
    private var correctAnswers = 0
    private var selectedAnswer = ""
    private var testTime = 40 * 60 * 1000

    val db = Firebase.firestore


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        startTimer()
        setupUI()
        admob()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(40 * 60 * 1000, 1000) {
            override fun onTick(p1: Long) {
                testTime -= 1000
                val p0 = p1 / 1000
                binding.ticker.text =
                    String.format(
                        "%02d:%02d",
                        (p0 - (((p0 / 60) / 60) / 24) * 24 * 60 * 60 - (((p0 / 60) / 60) % 24) * 60 * 60) / 60,
                        p0 % 60
                    )
            }

            override fun onFinish() {
                val bundle = Bundle()
                bundle.putInt("correctAnswers", correctAnswers)
                bundle.putInt("allAnswers", quizzes.size)
                bundle.putString("time", testTime.toString())
                findNavController().navigate(R.id.action_quizFragment_to_resultFragment)
            }
        }.start()
    }

    private fun setupUI() = with(binding) {
//        finish.setOnClickListener {
//            findNavController().navigate(R.id.action_quizFragment_to_resultFragment)
//        }

        answerA.setOnClickListener {
            selectedAnswer = answerA.text.toString()
            rightGroup.clearCheck()
        }

        answerC.setOnClickListener {
            selectedAnswer = answerC.text.toString()
            rightGroup.clearCheck()
        }

        answerE.setOnClickListener {
            selectedAnswer = answerE.text.toString()
            rightGroup.clearCheck()
        }

        answerB.setOnClickListener {
            selectedAnswer = answerB.text.toString()
            leftGroup.clearCheck()
        }

        answerD.setOnClickListener {
            selectedAnswer = answerD.text.toString()
            leftGroup.clearCheck()
        }

        answerF.setOnClickListener {
            selectedAnswer = answerF.text.toString()
            leftGroup.clearCheck()
        }


        next.setOnClickListener {

            if (currentQuestion < quizzes.size) {
                if (selectedAnswer == "") {
                    Toast.makeText(requireContext(), "Choose one option", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (quizzes[currentQuestion - 1].correctAnswer == selectedAnswer) {
                    correctAnswers++
                }
                selectedAnswer = ""

                currentQuestion++
                if (quizzes.isNotEmpty())
                    setQuestion()
            } else {

                if (quizzes[currentQuestion - 1].correctAnswer == selectedAnswer) {
                    correctAnswers++
                }

                val bundle = Bundle()
                bundle.putInt("correctAnswers", correctAnswers)
                bundle.putInt("allAnswers", quizzes.size)
                bundle.putString("time", testTime.toString())
                findNavController().navigate(R.id.action_quizFragment_to_resultFragment, bundle)
            }
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
                    if (Pref.level == "1")
                        if (quiz.level.toString() == "1" || quiz.level.toString() == "2")
                            quizzes.add(quiz)

                    if (Pref.level == "2")
                        if (quiz.level.toString() == "2" || quiz.level.toString() == "3")
                            quizzes.add(quiz)

                    if (Pref.level == "3")
                        if (quiz.level.toString() == "3" || quiz.level.toString() == "4")
                            quizzes.add(quiz)
                }
                currentQuestion = 1
                setQuestion()
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
    private fun setQuestion() = with(binding) {
        val data = quizzes[currentQuestion - 1]
        leftGroup.clearCheck()
        rightGroup.clearCheck()
        Log.d("TAG", "setQuestion: ${data.questionImg} ")
        Log.d("TAG", "setQuestion: ${data.question} ")
        Log.d("TAG", "setQuestion: ${data.id} ")
        Log.d("TAG", "setQuestion: ${data.level} ")


        if (data.questionImg != null) {
            if (!data.questionImg.isNullOrEmpty()) {
                questionImage.visibility = View.VISIBLE
                Picasso.get().load(data.questionImg).into(questionImage)
            } else questionImage.visibility = View.GONE
        } else questionImage.visibility = View.GONE

        questionCount.text = "$currentQuestion/${quizzes.size}"
        question.text = data.question
        answerA.text = data.answer1
        answerB.text = data.answer2
        answerC.text = data.answer3
        answerD.text = data.answer4
        answerE.text = data.answer5
        answerF.text = data.answer6
    }

    private fun clearAnswer() {

    }

    private fun setAnswer() {

    }


    private fun admob() {
        MobileAds.initialize(requireContext()) {}
        val mAdView: AdView = binding.adView

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        mAdView.adListener = object : AdListener() {
            override fun onAdClicked() {
                Log.d("TAG", "onAdClicked: ")
            }

            override fun onAdClosed() {
                Log.d("TAG", "onAdClosed: ")
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("TAG", "onAdFailedToLoad: ")
            }

            override fun onAdImpression() {
                Log.d("TAG", "onAdImpression: ")
            }

            override fun onAdLoaded() {
                Log.d("TAG", "onAdLoaded: ")
            }

            override fun onAdOpened() {
                Log.d("TAG", "onAdOpened: ")
            }
        }

    }
}