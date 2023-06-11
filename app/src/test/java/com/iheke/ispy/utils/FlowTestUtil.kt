package com.iheke.ispy.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest

object FlowTestUtil {
    /**
     * Collects the values emitted by a flow into a list.
     *
     * @param flow The flow to collect values from.
     * @return The list of emitted values.
     */
    suspend fun <T> collectData(flow: Flow<T>): List<T> {
        val testDispatcher = TestCoroutineDispatcher()
        val result = mutableListOf<T>()
        testDispatcher.runBlockingTest {
            flow.collect { value ->
                result.add(value)
            }
        }
        return result
    }
}
