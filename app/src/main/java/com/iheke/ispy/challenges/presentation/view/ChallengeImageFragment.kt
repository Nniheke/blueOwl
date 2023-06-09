package com.iheke.ispy.challenges.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import coil.load
import coil.request.CachePolicy
import com.iheke.ispy.R
import com.iheke.ispy.databinding.FragmentChallengeImageBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * The ChallengeImageFragment class is responsible for displaying an image associated with a challenge.
 */
@AndroidEntryPoint
class ChallengeImageFragment : Fragment() {

    private lateinit var binding: FragmentChallengeImageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_challenge_image, container, false)
        binding.title = arguments?.getString(TITLE)
        val imageUrl = arguments?.getString(IMAGE)
        if (imageUrl != null) {
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
        }

        return binding.root
    }

    companion object {
        const val IMAGE = "image"
        const val TITLE = "title"
    }
}