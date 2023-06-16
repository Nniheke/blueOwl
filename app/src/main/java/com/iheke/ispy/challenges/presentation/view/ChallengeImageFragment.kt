package com.iheke.ispy.challenges.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateViewModelFactory
import coil.load
import coil.request.CachePolicy
import com.iheke.ispy.R
import com.iheke.ispy.challenges.presentation.viewmodel.ChallengeViewModel
import com.iheke.ispy.databinding.FragmentChallengeImageBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * The ChallengeImageFragment class is responsible for displaying an image associated with a challenge.
 */

class ChallengeImageFragment : Fragment() {

    private val viewModel: ChallengeViewModel by activityViewModels{
        SavedStateViewModelFactory(requireActivity().application, this)
    }

    private lateinit var binding: FragmentChallengeImageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_challenge_image, container, false)
        val imageUrl = arguments?.getString(IMAGE) ?: viewModel.imageUrlLiveData.value
        val hint = arguments?.getString(TITLE) ?: viewModel.titleLiveData.value
        binding.title = hint
        binding.imageView.load(imageUrl) {
            diskCachePolicy(CachePolicy.ENABLED)
            listener(
                onStart = { _ ->
                    binding.progressBar.visibility = View.VISIBLE
                },
                onError = { _, _ ->
                    binding.progressBar.visibility = View.GONE
                },
                onSuccess = { _, _ ->
                    binding.progressBar.visibility = View.GONE
                }
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(savedInstanceState == null){
            val args = ChallengeImageFragmentArgs.fromBundle(arguments ?: Bundle())
            viewModel.setArguments(args.image, args.title)
        }
    }

    companion object {
        const val IMAGE = "image"
        const val TITLE = "title"
    }
}