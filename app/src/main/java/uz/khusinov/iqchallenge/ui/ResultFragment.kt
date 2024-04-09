package uz.khusinov.iqchallenge.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.firestore
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import uz.khusinov.iqchallenge.R
import uz.khusinov.iqchallenge.databinding.FragmentResultBinding
import uz.khusinov.iqchallenge.models.Rating
import uz.khusinov.iqchallenge.utills.Pref
import uz.khusinov.iqchallenge.utills.viewBinding
import java.util.concurrent.TimeUnit

class ResultFragment : Fragment(R.layout.fragment_result) {
    private val binding by viewBinding { FragmentResultBinding.bind(it) }
    val db = com.google.firebase.Firebase.firestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    @SuppressLint("SetTextI18n")
    private fun setupUI() = with(binding) {

        val correctAnswers = arguments?.getInt("correctAnswers")
        val time = arguments?.getString("time")

        if (correctAnswers != null && time != null) {
            val rate = correctAnswers * 4
            val timeSpend = (40*60*1000 - time.toLong())/1000

            iq.text = rate.toString()
            binding.time.text = "$timeSpend sec"

            val rating = Rating(Pref.id, Pref.name, rate)
            db.collection("rating")
                .add(rating)
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }
        }

        leaderboard.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_leaderBoardFragment)
        }

        retry.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_quizFragment)
        }

        home.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_homeFragment)
        }

        konfettiView.start(
            Party(
                speed = 0f,
                maxSpeed = 30f,
                damping = 0.9f,
                spread = 360,
                colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                emitter = Emitter(duration = 2000, TimeUnit.MILLISECONDS).max(500),
                position = Position.Relative(0.5, 0.0)
            )
        )


    }
}