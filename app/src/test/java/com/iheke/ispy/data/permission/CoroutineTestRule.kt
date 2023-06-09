package com.iheke.ispy.data.permission

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * The `CoroutineTestRule` class is a JUnit rule used for testing coroutines with a specific test dispatcher.
 * It is annotated with `@ExperimentalCoroutinesApi` to indicate that it uses experimental coroutine APIs.
 *
 * The `CoroutineTestRule` extends `TestWatcher` and implements `TestCoroutineScope`, allowing it to be used as
 * a JUnit rule and providing a coroutine scope for managing coroutines in tests.
 *
 * The main purpose of the `CoroutineTestRule` is to set up and tear down the test environment for coroutines.
 * It overrides the `starting` and `finished` methods from `TestWatcher` to set the test dispatcher as the main
 * dispatcher using `Dispatchers.setMain`, and then cleans up the test coroutines and resets the main dispatcher
 * in the `finished` method.
 *
 * By using the `CoroutineTestRule` in a test class, you can write and run coroutines in a controlled environment
 * and ensure deterministic behavior of suspending functions and other coroutine-related operations during testing.
 *
 * @param testDispatcher The `TestCoroutineDispatcher` to be used as the test dispatcher for coroutines.
 */
@ExperimentalCoroutinesApi
class CoroutineTestRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher(), TestCoroutineScope by TestCoroutineScope(testDispatcher) {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}
