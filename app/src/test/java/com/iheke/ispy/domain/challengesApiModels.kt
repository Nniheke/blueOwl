package com.iheke.ispy.domain

import com.iheke.ispy.challenges.data.models.ChallengeLocation
import com.iheke.ispy.challenges.data.models.ChallengesApiModel
import com.iheke.ispy.challenges.data.models.Match
import com.iheke.ispy.challenges.data.models.Rating

val challengesApiModels = listOf(
    ChallengesApiModel(
        id = "1",
        matches = listOf(
            Match(
                "1",
                ChallengeLocation(501.00, -612.0),
                "challenge_photo1.jpg",
                verified = true,
                "1"
            ),
            Match(
                "2",
                ChallengeLocation(567.0, -698.0),
                "challenge_photo2.jpg",
                verified = false,
                "1"
            ),
            Match(
                "3",
                ChallengeLocation(587.0, -634.0),
                "challenge_photo3.jpg",
                verified = true,
                "1"
            )
        ),
        ratings = listOf(
            Rating("1", value = 3, "1"),
            Rating("1", value = 5, "1"),
            Rating("1", value = 4, "1")
        ),
        hint = "Some hint",
        photo = "challenge_photo.jpg",
        user = "1",
        location = ChallengeLocation(521.0, -623.0)
    ),
    ChallengesApiModel(
        id = "2",
        matches = listOf(
            Match(
                "4",
                ChallengeLocation(345.0, -142.0),
                "challenge_photo4.jpg",
                verified = true,
                "2"
            ),
            Match(
                "5",
                ChallengeLocation(423.0, -564.0),
                "challenge_photo5.jpg",
                verified = true,
                "2"
            ),
            Match(
                "6",
                ChallengeLocation(713.0, -238.0),
                "challenge_photo6.jpg",
                verified = true,
                "2"
            )
        ),
        ratings = listOf(
            Rating("2", value = 3, "2"),
            Rating("2", value = 5, "2"),
            Rating("2", value = 4, "2")
        ),
        hint = "Some hint2",
        photo = "challenge_photo2.jpg",
        user = "2",
        location = ChallengeLocation(564.0, -426.0)
    )
)