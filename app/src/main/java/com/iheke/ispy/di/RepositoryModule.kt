package com.iheke.ispy.di

import com.iheke.ispy.challenges.data.api.ISpyService
import com.iheke.ispy.challenges.data.permission.PermissionService
import com.iheke.ispy.challenges.data.repositories.challenges.ChallengesRepository
import com.iheke.ispy.challenges.data.repositories.challenges.ChallengesRepositoryImpl
import com.iheke.ispy.challenges.data.repositories.challenges.datasource.ChallengesRemoteDataSource
import com.iheke.ispy.challenges.data.repositories.challenges.datasourceimpl.ChallengesRemoteDataSourceImpl
import com.iheke.ispy.challenges.data.repositories.permissions.PermissionRepository
import com.iheke.ispy.challenges.data.repositories.permissions.PermissionRepositoryImpl
import com.iheke.ispy.challenges.data.repositories.users.UsersRepository
import com.iheke.ispy.challenges.data.repositories.users.UsersRepositoryImpl
import com.iheke.ispy.data.repositories.challenges.datasource.UsersRemoteDataSource
import com.iheke.ispy.data.repositories.challenges.datasourceimpl.UsersRemoteDataSourceImpl
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
     * @return An instance of the ChallengesRepository.
     */
    @Provides
    fun provideChallengesRepository(challengesRemoteDataSource: ChallengesRemoteDataSource): ChallengesRepository {
        return ChallengesRepositoryImpl(challengesRemoteDataSource)
    }

    /**
     * Provides an instance of the UsersRepository.
     *
     * @param usersRemoteDataSource The UsersRemoteDataSource dependency.
     * @return An instance of the UsersRepository.
     */
    @Provides
    fun provideUsersRepository(usersRemoteDataSource: UsersRemoteDataSource): UsersRepository {
        return UsersRepositoryImpl(usersRemoteDataSource)
    }
}


