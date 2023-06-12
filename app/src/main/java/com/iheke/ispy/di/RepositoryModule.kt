package com.iheke.ispy.di

import com.iheke.ispy.challenges.data.api.ISpyService
import com.iheke.ispy.challenges.data.permission.PermissionService
import com.iheke.ispy.challenges.data.repository.repositories.challenge.ChallengeRepository
import com.iheke.ispy.challenges.data.repository.repositories.challenge.ChallengeRepositoryImpl
import com.iheke.ispy.challenges.data.repository.datasource.challenge.ChallengesRemoteDataSource
import com.iheke.ispy.challenges.data.repository.datasourceimpl.challenge.ChallengesRemoteDataSourceImpl
import com.iheke.ispy.challenges.data.repository.repositories.permission.PermissionRepository
import com.iheke.ispy.challenges.data.repository.repositories.permission.PermissionRepositoryImpl
import com.iheke.ispy.challenges.data.repository.datasource.user.UsersRemoteDataSource
import com.iheke.ispy.challenges.data.repository.datasourceimpl.user.UsersRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Dagger module for providing dependencies related to repositories.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * Provides an instance of the ChallengesRemoteDataSource.
     *
     * @param iSpyService The ISpyService dependency.
     * @return An instance of the ChallengesRemoteDataSource.
     */
    @Provides
    fun provideChallengesDataSource(iSpyService: ISpyService): ChallengesRemoteDataSource {
        return ChallengesRemoteDataSourceImpl(iSpyService)
    }

    /**
     * Provides an instance of the UsersRemoteDataSource.
     *
     * @param iSpyService The ISpyService dependency.
     * @return An instance of the UsersRemoteDataSource.
     */
    @Provides
    fun provideUsersDataSource(iSpyService: ISpyService): UsersRemoteDataSource {
        return UsersRemoteDataSourceImpl(iSpyService)
    }

    /**
     * Provides an instance of the PermissionRepository.
     *
     * @param permissionService The PermissionService dependency.
     * @return An instance of the PermissionRepository.
     */
    @Provides
    fun providePermissionRepository(permissionService: PermissionService): PermissionRepository {
        return PermissionRepositoryImpl(permissionService)
    }

    /**
     * Provides an instance of the ChallengesRepository.
     *
     * @param challengesRemoteDataSource The ChallengesRemoteDataSource dependency.
     * @param usersRemoteDataSource: The UsersRemoteDataSource dependency.
     * @return An instance of the ChallengesRepository.
     */


    @Provides
    fun provideChallengeRepository(challengesRemoteDataSource: ChallengesRemoteDataSource, usersRemoteDataSource: UsersRemoteDataSource): ChallengeRepository {
        return ChallengeRepositoryImpl(challengesRemoteDataSource, usersRemoteDataSource)
    }

}


