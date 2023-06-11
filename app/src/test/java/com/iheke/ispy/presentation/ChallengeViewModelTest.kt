package com.iheke.ispy.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.iheke.ispy.challenges.domain.mappers.toUiModel
import com.iheke.ispy.challenges.domain.permission.Permission
import com.iheke.ispy.challenges.domain.permission.PermissionState
import com.iheke.ispy.challenges.domain.usecases.FetchChallengesUseCase
import com.iheke.ispy.challenges.domain.usecases.GetLocationUseCase
import com.iheke.ispy.challenges.domain.usecases.PermissionUseCase
import com.iheke.ispy.challenges.presentation.event.Event
import com.iheke.ispy.challenges.presentation.model.UiModel
import com.iheke.ispy.challenges.presentation.model.UserUiModel
import com.iheke.ispy.challenges.presentation.viewmodel.ChallengeViewModel
import com.iheke.ispy.data.challenge.challengesApiModels
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ChallengeViewModelTest {
    @get:Rule
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()


    private lateinit var fetchChallengesUseCase: FetchChallengesUseCase
    private lateinit var permissionUseCase: PermissionUseCase
    private lateinit var getLocationUseCase: GetLocationUseCase
    private lateinit var viewModel: ChallengeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fetchChallengesUseCase = mockk()
        permissionUseCase = mockk()
        getLocationUseCase = mockk()
        viewModel = ChallengeViewModel(
            fetchChallengesUseCase,
            permissionUseCase,
            getLocationUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `requestPermissions should call permissionUseCase requestPermissions`() = runBlocking {
        // Arrange
        val permissions = setOf(
            Permission("android.permission.ACCESS_FINE_LOCATION", PermissionState.GRANTED)
        )

        // Act
        viewModel.requestPermissions(permissions)

        // Assert
        coVerify { permissionUseCase.execute(permissions) }
    }

    @Test
    fun `retrieveCurrentLocation should update location and fetch challenges`() = runBlocking {

        viewModel.retrieveCurrentLocation()

        coVerify { getLocationUseCase.execute() }
    }

    @Test
    fun `updateViewStateOnChallengesLoaded should update viewState with provided challengeUiModels`() {
        // Arrange
        val articles = listOf(
            UiModel(UserUiModel(""), challengesApiModels.first().toUiModel(), 10.0),
            UiModel(UserUiModel(""), challengesApiModels.first().toUiModel(), 5.0)
        )

        // Act
        viewModel.updateViewStateOnChallengesLoaded(articles)

        // Assert
        val updatedViewState = viewModel.viewState.value
        assertEquals(articles.first().toUiModel(), updatedViewState.challengeUiModel.first())
        assertEquals(false, updatedViewState.isLoading)
    }

    @Test
    fun `onChallengeClicked should emit OpenChallenge event with the given image URL`() =
        testDispatcher.runBlockingTest {
            // Arrange
            val imageUrl = "https://example.com/image.jpg"
            val hint = "Some test title"
            val eventDeferred = CompletableDeferred<Event?>()
            val expectedEvent = Event.OpenChallenge(imageUrl, hint)

            val collectJob = launch {
                viewModel.viewEvent.collect { event ->
                    eventDeferred.complete(event)
                }
            }

            // Act
            val clickJob = launch {
                viewModel.onChallengeClicked(imageUrl, hint)
            }

            // Assert
            val actualEvent = eventDeferred.await()
            assertTrue(actualEvent is Event.OpenChallenge)
            assertEquals(
                expectedEvent.imageUrl,
                (actualEvent as Event.OpenChallenge).imageUrl
            )

            // Cleanup
            collectJob.cancel()
            clickJob.cancel()
        }


    @Test
    fun `onLocationGranted should emit LocationPermissionGranted event with the given permission status`() =
        testDispatcher.runBlockingTest {
            // Arrange
            val granted = true

            val eventDeferred = CompletableDeferred<Event?>()

            val expectedEvent = Event.LocationPermissionGranted(granted)

            // Act

            val collectJob = launch {
                viewModel.viewEvent.collect { event ->
                    eventDeferred.complete(event)
                }
            }

            val permissionJob = launch {
                viewModel.onLocationGranted(granted)
            }

            // Assert
            val actualEvent = eventDeferred.await()
            assertTrue(actualEvent is Event.LocationPermissionGranted)
            assertEquals(
                expectedEvent.granted,
                (actualEvent as Event.LocationPermissionGranted).granted
            )

            // Cleanup
            collectJob.cancel()
            permissionJob.cancel()

        }

}
