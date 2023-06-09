package com.iheke.ispy.challenges.data.repositories.users

import com.iheke.ispy.challenges.data.models.UserApiModel
import com.iheke.ispy.data.repositories.challenges.datasource.UsersRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(private val usersRemoteDataSource: UsersRemoteDataSource) :
    UsersRepository {
    /**
     * Retrieves a flow of user data.
     *
     * @return A flow emitting a list of UserApiModel objects.
     */
    override suspend fun getUsers(): Flow<List<UserApiModel>> {
        return usersRemoteDataSource.getUsers()
    }
}