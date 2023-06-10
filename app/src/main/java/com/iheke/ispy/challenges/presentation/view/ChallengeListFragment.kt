package com.iheke.ispy.challenges.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.iheke.ispy.R
import com.iheke.ispy.challenges.presentation.viewmodel.ChallengeViewModel
import com.iheke.ispy.challenges.presentation.state.ChallengesViewState
import com.iheke.ispy.challenges.presentation.event.Event
import com.iheke.ispy.challenges.presentation.adapter.ChallengeAdapter
import com.iheke.ispy.challenges.presentation.model.ChallengeUiModel
import com.iheke.ispy.databinding.FragmentChallengeListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * The ChallengeListFragment class is responsible for displaying a list of challenges.
 */
@AndroidEntryPoint
class ChallengeListFragment : Fragment() {

    private val viewModel: ChallengeViewModel by activityViewModels()
    private lateinit var binding: FragmentChallengeListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_challenge_list, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.challengesViewState = ChallengesViewState()
        binding.recyclerViewChallenges.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.near_me)
        startObservers()
    }

    /**
     * Observes the challenge state in the view model and updates the UI accordingly.
     */
    private fun observeChallengeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collect { state ->
                binding.challengesViewState = state
                updateChallenges(state.challengeUiModel)
            }
        }
    }

    /**
     * Observes the challenge events in the view model and performs corresponding actions.
     */
    private fun observeChallengeEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewEvent.collect { event ->
                when (event) {
                    is Event.OpenChallenge -> {
                        val action =
                            ChallengeListFragmentDirections.actionChallengeListFragmentToChallengeImageFragment(
                                event.imageUrl,
                                event.hint
                            )
                        val navController = activity?.findNavController(R.id.nav_host_fragment)
                        navController?.navigate(action)
                    }
                    is Event.LocationPermissionGranted -> {
                        // Handle location permission granted event
                    }
                }
            }
        }
    }

    /**
     * Starts observing the challenge state and events.
     */
    private fun startObservers() {
        observeChallengeState()
        observeChallengeEvent()
    }

    /**
     * Updates the challenges in the UI with the given list of challenge UI models.
     *
     * @param challenges The list of challenge UI models to be displayed.
     */
    private fun updateChallenges(challenges: List<ChallengeUiModel>) {
        val adapter = ChallengeAdapter(challenges, viewModel)
        binding.recyclerViewChallenges.adapter = adapter
    }
}
