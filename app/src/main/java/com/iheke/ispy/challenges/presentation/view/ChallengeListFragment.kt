package com.iheke.ispy.challenges.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.iheke.ispy.R
import com.iheke.ispy.challenges.presentation.adapters.ChallengeAdapter
import com.iheke.ispy.challenges.presentation.event.Event
import com.iheke.ispy.challenges.presentation.model.ChallengeUiModel
import com.iheke.ispy.challenges.presentation.viewmodel.ChallengeViewModel
import com.iheke.ispy.databinding.FragmentChallengeListBinding
import kotlinx.coroutines.launch

/**
 * The ChallengeListFragment class is responsible for displaying a list of challenges.
 */
class ChallengeListFragment : Fragment(), ChallengeClickListener {

    private val viewModel: ChallengeViewModel by activityViewModels()
    private lateinit var binding: FragmentChallengeListBinding
    private val challengeUiModel: MutableList<ChallengeUiModel> = mutableListOf()
    private lateinit var adapter: ChallengeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_challenge_list, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerViewChallenges.layoutManager = LinearLayoutManager(this.activity)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title = getString(R.string.near_me)
        initializeAdapter()
        startObservers()
    }

    /**
     * Observes the challenge state in the view model and updates the UI accordingly.
     */
    private fun observeChallengeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collect { state ->
                viewModel.viewState.collect { state ->
                    binding.challengesViewState = state
                    challengeUiModel.clear()
                    challengeUiModel.addAll(state.challengeUiModel)
                    adapter.notifyDataSetChanged()
                }
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
                    is Event.LocationPermissionGranted -> {}
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
     * Initializes the adapter with an empty list of challenges.
     */
    private fun initializeAdapter() {
        adapter = ChallengeAdapter(
            challengeUiModel, this
        )
        binding.recyclerViewChallenges.adapter = adapter
    }

    override fun onClick(image: String, hint: String) {
        viewModel.onChallengeClicked(image, hint)
    }

}
