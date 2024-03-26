package uz.khusinov.iqchallenge.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import uz.khusinov.iqchallenge.R
import uz.khusinov.iqchallenge.databinding.RatingItemBinding
import uz.khusinov.iqchallenge.models.Rating


class RatingAdapter  : RecyclerView.Adapter<RatingAdapter.ViewHolder>() {

    private val dif = AsyncListDiffer(this, ITEM_DIFF)

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RatingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dif.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.setContent(position)

    inner class ViewHolder(private val binding: RatingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
        fun setContent(position: Int) = with(binding) {
            val data = dif.currentList[position]
            username.text = "${position+1}. ${data.username}"
            userRate.text = data.rate.toString()
            if (position == 0) {
                medal.isVisible = true
                medal.setColorFilter(binding.root.context.getColor(R.color.gold))
                username.setTextColor(binding.root.context.getColor(R.color.gold))
                userRate.setTextColor(binding.root.context.getColor(R.color.gold))
            }

            if (position == 1) {
                medal.isVisible = true
                medal.setColorFilter(binding.root.context.getColor(R.color.silver))
                username.setTextColor(binding.root.context.getColor(R.color.silver))
                userRate.setTextColor(binding.root.context.getColor(R.color.silver))
            }

            if (position == 2) {
                medal.isVisible = true
                medal.setColorFilter(binding.root.context.getColor(R.color.bronze))
                username.setTextColor(binding.root.context.getColor(R.color.bronze))
                userRate.setTextColor(binding.root.context.getColor(R.color.bronze))
            }
        }
    }

    fun submitList(data: List<Rating>) = dif.submitList(data)

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Rating>() {
            override fun areItemsTheSame(oldItem: Rating, newItem: Rating): Boolean =
                oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Rating, newItem: Rating): Boolean =
                oldItem == newItem
        }
    }
}