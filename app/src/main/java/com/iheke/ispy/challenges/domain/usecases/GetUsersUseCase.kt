package com.iheke.ispy.challenges.domain.usecases

import com.iheke.ispy.challenges.data.models.UserApiModel
import com.iheke.ispy.challenges.data.repositories.users.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * The GetUsersUseCase class is responsible for executing the logic to retrieve users.
 *
 * @param usersRepository The UsersRepository implementation to retrieve users from.
 */
class GetUsersUseCase @Inject constructor(private val usersRepository: UsersRepository) {
    /**
     * Executes the use case to retrieve users.
     *
     * @return A flow emitting a list of UserApiModel objects.
     */
    suspend fun execute(): Flow<List<UserApiModel>> = usersRepository.getUsers()
}