package com.iheke.ispy.utils

import com.iheke.ispy.challenges.data.models.Match
import com.iheke.ispy.challenges.data.models.Rating
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object MapperUtils {
    /**
     * Calculates the distance between two sets of latitude and longitude coordinates using the Haversine formula.
     *
     * @param currentLat The latitude of the current location.
     * @param currentLong The longitude of the current location.
     * @param challengeLat The latitude of the challenge location.
     * @param challengeLong The longitude of the challenge location.
     * @return The distance between the two locations in meters.
     */
    fun calculateDistance(
        currentLat: Double,
        currentLong: Double,
        challengeLat: Double,
        challengeLong: Double
    ): Double {
        val earthRadius = 6371

        val latDistance = Math.toRadians(challengeLat - currentLat)
        val longDistance = Math.toRadians(challengeLong - currentLong)

        val a = sin(latDistance / 2) * sin(latDistance / 2) +
                cos(Math.toRadians(currentLat)) * cos(Math.toRadians(challengeLat)) *
                sin(longDistance / 2) * sin(longDistance / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c * 1000
    }

    /**
     * Calculates the number of wins based on the verified matches.
     *
     * @param matches The list of Match objects representing the matches for the challenge.
     * @return The number of wins.
     */
     fun calculateNumberOfWins(matches: List<Match>): Int {
        return matches.count { it.verified }
    }

    /**
     * Calculates the average rating based on the ratings given for the challenge.
     *
     * @param ratings The list of Rating objects representing the ratings for the challenge.
     * @return The average rating.
     */
     fun calculateAverageRating(ratings: List<Rating>): Double {
        if (ratings.isNotEmpty()) {
            val totalRating = ratings.sumBy { it.value }
            return (totalRating / ratings.size).toDouble()
        }
        return 1.00
    }
}
