package uz.khusinov.iqchallenge.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.firestore
import uz.khusinov.iqchallenge.R
import uz.khusinov.iqchallenge.adapters.RatingAdapter
import uz.khusinov.iqchallenge.databinding.FragmentLeaderBoardBinding
import uz.khusinov.iqchallenge.models.Rating
import uz.khusinov.iqchallenge.utills.viewBinding

class LeaderBoardFragment : Fragment(R.layout.fragment_leader_board) {
    private val binding by viewBinding { FragmentLeaderBoardBinding.bind(it) }
    private val adapter by lazy { RatingAdapter() }
    private var dataList = mutableListOf<Rating>()
    val db = com.google.firebase.Firebase.firestore


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        setupUI()
    }

    private fun loadData() {

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
                ratings.groupBy { it.id }
                    .forEach { (_, value) -> value.sortedByDescending { it.rate } }
                adapter.submitList(ratings)
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }

    private fun setupUI() = with(binding) {
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        ratingRecycler.adapter = adapter
    }
}