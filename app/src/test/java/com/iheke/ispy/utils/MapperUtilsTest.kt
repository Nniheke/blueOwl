package com.iheke.ispy.utils

import com.iheke.ispy.challenges.data.models.ChallengeLocation
import com.iheke.ispy.challenges.data.models.Match
import com.iheke.ispy.challenges.data.models.Rating
import org.junit.Assert.assertEquals
import org.junit.Test

class MapperUtilsTest {

    @Test
    fun calculateDistance_shouldReturnCorrectDistance() {
        // Test case values
        val currentLat = 40.7128
        val currentLong = -74.0060
        val challengeLat = 34.0522
        val challengeLong = -118.2437

        // Expected result
        val expectedDistance = 3935746.254609722

        // Calculate the distance
        val distance = MapperUtils.calculateDistance(currentLat, currentLong, challengeLat, challengeLong)

        // Assert the result
        assertEquals(expectedDistance, distance, 0.01)
    }



    @Test
    fun calculateNumberOfWins_shouldReturnCorrectNumberOfWins() {
        // Test case values
        val matches = listOf(
            Match(id="",verified = true, location = ChallengeLocation(5.00,6.00), photo = "", user = ""),
            Match(id="",verified = true,location = ChallengeLocation(5.00,6.00), photo = "", user = ""),
            Match(id="",verified = false, location = ChallengeLocation(5.00,6.00), photo = "", user = ""),
            Match(id="",verified = true, location = ChallengeLocation(5.00,6.00), photo = "", user = ""),
            Match(id="",verified = false, location = ChallengeLocation(5.00,6.00), photo = "", user = "")
        )

        // Expected result
        val expectedWins = 3

        // Calculate the number of wins
        val wins = MapperUtils.calculateNumberOfWins(matches)

        // Assert the result
        assertEquals(expectedWins, wins)
    }

    @Test
    fun calculateAverageRating_shouldReturnCorrectAverageRating() {
        // Test case values
        val ratings = listOf(
            Rating("",value = 4,""),
            Rating("",value = 3,""),
            Rating("",value = 5,""),
            Rating("",value = 2,""),
            Rating("",value = 4,"")
        )

        // Expected result
        val expectedAverageRating = 3.0

        // Calculate the average rating
        val averageRating = MapperUtils.calculateAverageRating(ratings)

        // Assert the result
        assertEquals(expectedAverageRating, averageRating, 0.01)
    }
}
