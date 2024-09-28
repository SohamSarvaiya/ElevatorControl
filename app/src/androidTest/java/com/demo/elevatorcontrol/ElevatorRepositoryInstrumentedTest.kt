package com.demo.elevatorcontrol

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.demo.elevatorcontrol.models.DoorState
import com.demo.elevatorcontrol.repository.ElevatorRepository
import kotlinx.coroutines.flow.toList
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After


@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ElevatorRepositoryInstrumentedTest {

    private lateinit var elevatorRepository: ElevatorRepository
    private lateinit var testDispatcher: TestCoroutineDispatcher

    @Before
    fun setup() {
        elevatorRepository = ElevatorRepository()
        testDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testDispatcher) // Set the main dispatcher to the test dispatcher
    }


    @Test
    fun testElevatorClosesDoorsAfterOpen() = runBlockingTest {
        // Request the elevator to the 2nd floor
        val flow = elevatorRepository.requestElevator(2)

        // Collect the emitted states into a list
        val results = flow.toList()

        // Assert that the doors were opened and then closed
        assertEquals(DoorState.OPEN, results[results.size - 2].doorState) // Doors should be open before closing
        assertEquals(DoorState.CLOSED, results.last().doorState) // Doors should be closed at the end
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher
        testDispatcher.cleanupTestCoroutines() // Clean up the test dispatcher
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.demo.elevatorcontrol", appContext.packageName)
    }
}
