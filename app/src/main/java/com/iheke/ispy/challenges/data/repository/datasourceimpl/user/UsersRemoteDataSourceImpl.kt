package com.iheke.ispy.challenges.data.repository.datasourceimpl.user

import com.iheke.ispy.challenges.data.api.ISpyService
import com.iheke.ispy.challenges.data.models.UserApiModel
import com.iheke.ispy.challenges.data.repository.datasource.user.UsersRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class UsersRemoteDataSourceImpl @Inject constructor(private val iSpyService: ISpyService) :
    UsersRemoteDataSource {
    /**
     * Retrieves a flow of user data from the remote data source.
     *
     * @return A flow emitting a list of UserApiModel objects.
     */
    override suspend fun getUsers(): Flow<List<UserApiModel>> {
        return flowOf(iSpyService.getUsers())
    }
}