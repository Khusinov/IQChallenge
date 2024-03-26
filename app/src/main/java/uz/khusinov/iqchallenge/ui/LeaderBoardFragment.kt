package uz.khusinov.iqchallenge.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.khusinov.iqchallenge.R
import uz.khusinov.iqchallenge.adapters.RatingAdapter
import uz.khusinov.iqchallenge.databinding.FragmentLeaderBoardBinding
import uz.khusinov.iqchallenge.models.Rating
import uz.khusinov.iqchallenge.utills.viewBinding

class LeaderBoardFragment : Fragment(R.layout.fragment_leader_board) {
    private val binding by viewBinding { FragmentLeaderBoardBinding.bind(it) }
    private val adapter by lazy { RatingAdapter() }
    private var dataList = mutableListOf<Rating>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        setupUI()
    }

    private fun loadData() {
        dataList.clear()
        dataList.add(Rating("1", "Bekdiyor", 278))
        dataList.add(Rating("2", "Shaxriyor", 269))
        dataList.add(Rating("3", "Rustam", 255))
        dataList.add(Rating("4", "Asad", 241))
        dataList.add(Rating("5", "Lazizbek", 226))
        dataList.add(Rating("6", "Elmurod", 217))
        adapter.submitList(dataList)
    }

    private fun setupUI() = with(binding) {
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        ratingRecycler.adapter = adapter
    }
}