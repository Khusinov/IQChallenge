package uz.khusinov.iqchallenge.ui

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
import uz.khusinov.iqchallenge.utills.viewBinding
import java.util.concurrent.TimeUnit

class ResultFragment : Fragment(R.layout.fragment_result) {
    private val binding by viewBinding { FragmentResultBinding.bind(it) }
    val db = com.google.firebase.Firebase.firestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() = with(binding) {

        val ratings = mutableListOf<Rating>()

        db.collection("rating")
            .get()
            .addOnSuccessListener { result ->
                ratings.clear()
                for (i in result) {
                    val rating = Rating(
                        i.data["id"].toString(),
                        i.data["username"].toString(),
                        i.data["rate"].toString().toInt(),
                    )
                    ratings.add(rating)
                }
                Log.d("TAG", "setupUI: tayyor ${ratings.size} ")
            }

            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
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