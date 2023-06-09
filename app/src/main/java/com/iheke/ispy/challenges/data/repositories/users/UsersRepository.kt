package com.iheke.ispy.challenges.data.repositories.users

import com.iheke.ispy.challenges.data.models.UserApiModel
import kotlinx.coroutines.flow.Flow

/**
 * The UsersRepository interface provides methods for retrieving user data.
 */
interface UsersRepository {
    /**
     * Retrieves a flow of user data.
     *
     * @return A flow emitting a list of UserApiModel objects.
     */
    suspend fun getUsers(): Flow<List<UserApiModel>>
}