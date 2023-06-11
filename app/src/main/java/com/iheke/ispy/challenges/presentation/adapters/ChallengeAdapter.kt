package com.iheke.ispy.challenges.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iheke.ispy.challenges.presentation.viewmodel.ChallengeViewModel
import com.iheke.ispy.challenges.presentation.model.ChallengeUiModel
import com.iheke.ispy.databinding.ChallengeCardBinding

/**
 * The ChallengeAdapter class is responsible for binding ChallengeUiModel data to the RecyclerView.
 *
 * @param challenges The list of ChallengeUiModel items to be displayed.
 * @param viewModel The ChallengeViewModel instance associated with the adapter.
 */
class ChallengeAdapter(
    private val challenges: List<ChallengeUiModel>,
    private val viewModel: ChallengeViewModel
) : RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ChallengeCardBinding.inflate(inflater, parent, false)
        return ChallengeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val challenge = challenges[position]
        holder.bind(challenge, viewModel)
    }

    override fun getItemCount(): Int {
        return challenges.size
    }

    /**
     * The ChallengeViewHolder class represents a ViewHolder for a ChallengeUiModel item in the RecyclerView.
     *
     * @param binding The ChallengeCardBinding instance associated with the ViewHolder.
     */
    class ChallengeViewHolder(private val binding: ChallengeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds the ChallengeUiModel data to the ViewHolder.
         *
         * @param challenge The ChallengeUiModel item to be bound.
         * @param viewModel The ChallengeViewModel instance associated with the ViewHolder.
         */
        fun bind(challenge: ChallengeUiModel, viewModel: ChallengeViewModel) {
            binding.viewModel = viewModel
            binding.challenge = challenge
            binding.executePendingBindings()
        }
    }
}
