package com.iheke.ispy.di

import com.iheke.ispy.challenges.domain.mappers.ChallengeMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Dagger module for providing dependencies related to mappers.
 */
@Module
@InstallIn(SingletonComponent::class)
object MapperModule {
    /**
     * Provides an instance of the ChallengeMapper.
     *
     * @return An instance of the ChallengeMapper.
     */
    @Provides
    fun provideChallengeMapper(): ChallengeMapper {
        return ChallengeMapper()
    }
}

