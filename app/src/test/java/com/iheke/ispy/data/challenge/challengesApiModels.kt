package com.iheke.ispy.data.challenge

import com.iheke.ispy.challenges.data.models.ChallengeLocation
import com.iheke.ispy.challenges.data.models.ChallengesApiModel
import com.iheke.ispy.challenges.data.models.Match
import com.iheke.ispy.challenges.data.models.Rating

val challengesApiModels = listOf(
    ChallengesApiModel(
        "1", "photo.jpg", "alcatraz", "1", ChallengeLocation(5.0, 6.0),
        listOf(Match("", ChallengeLocation(5.0, 6.0), "photo.jpg", true, "1")),
        listOf(Rating("1", 3, "1"))
    ),
    ChallengesApiModel(
        "2", "photos.jpg", "alcatraz", "2", ChallengeLocation(5.0, 6.0),
        listOf(Match("", ChallengeLocation(5.0, 6.0), "photo2.jpg", true, "2")),
        listOf(Rating("2", 4, "2"))
    )
)