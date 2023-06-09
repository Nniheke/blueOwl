package com.iheke.ispy.data.repositories.challenges.datasource

import com.iheke.ispy.challenges.data.models.UserApiModel
import kotlinx.coroutines.flow.Flow

/**
 * The UsersRemoteDataSource interface provides methods for retrieving user data from a remote data source.
 */
interface UsersRemoteDataSource {
    /**
     * Retrieves a flow of user data from the remote data source.
     *
     * @return A flow emitting a list of UserApiModel objects.
     */
    suspend fun getUsers(): Flow<List<UserApiModel>>
}