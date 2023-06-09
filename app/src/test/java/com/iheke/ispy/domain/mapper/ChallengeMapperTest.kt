package com.iheke.ispy.domain.mapper

import com.iheke.ispy.challenges.data.models.*
import com.iheke.ispy.challenges.domain.mappers.ChallengeMapper
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ChallengeMapperTest {

    private val mapper = ChallengeMapper()

    @Test
    fun `mapToUiModel returns correct ChallengeUiModel`() {
        // Arrange
        val challengesApiModel = ChallengesApiModel(
            id = "1",
            matches = listOf(
                Match(
                    "1",
                    ChallengeLocation(5.0, 6.0),
                    "challenge_photo1.jpg",
                    verified = true,
                    "1"
                ),
                Match(
                    "2",
                    ChallengeLocation(5.0, 6.0),
                    "challenge_photo2.jpg",
                    verified = false,
                    "2"
                ),
                Match(
                    "3",
                    ChallengeLocation(5.0, 6.0),
                    "challenge_photo3.jpg",
                    verified = true,
                    "3"
                )
            ),
            ratings = listOf(
                Rating("", value = 3, ""),
                Rating("", value = 5, ""),
                Rating("", value = 4, "")
            ),
            hint = "Some hint",
            photo = "challenge_photo.jpg",
            user = "1",
            location = ChallengeLocation(5.0, 6.0)
        )

        val userApiModel = UserApiModel("1", "test@email.com", "Creator")
        val distance = 10.0

        // Act
        val result = mapper.mapToUiModel(challengesApiModel, userApiModel, distance)

        // Assert
        assertEquals(2, result.wins)
        assertEquals(4.0, result.rating, 0.01)
        assertEquals(distance, result.distance, 0.0)
        assertEquals("Some hint", result.hint)
        assertEquals("Creator", result.creator)
        assertEquals("challenge_photo.jpg", result.image)
    }
}