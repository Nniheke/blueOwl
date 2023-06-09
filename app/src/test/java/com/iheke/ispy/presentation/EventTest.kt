package com.iheke.ispy.presentation

import com.iheke.ispy.challenges.presentation.event.Event
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class EventTest {

    @Test
    fun `OpenChallenge event should contain the given image URL`() = runBlockingTest {
        // Arrange
        val imageUrl = "https://example.com/image.jpg"
        val hint = "Some Hint"
        val event = Event.OpenChallenge(imageUrl, hint)
        // Assert
        assertEquals(imageUrl, event.imageUrl)
    }

    @Test
    fun `LocationPermissionGranted event should contain the granted flag`() = runBlockingTest {
        // Arrange
        val granted = true
        val event = Event.LocationPermissionGranted(granted)

        // Assert
        assertEquals(granted, event.granted)
    }

}
