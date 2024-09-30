package com.demo.elevatorcontrol

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.demo.elevatorcontrol.models.DoorState
import com.demo.elevatorcontrol.models.ElevatorState
import com.demo.elevatorcontrol.repository.ElevatorRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertTrue


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class ElevatorRepositoryInstrumentedForFloorTest1 {

    private lateinit var elevatorRepository: ElevatorRepository
    private lateinit var testDispatcher: TestCoroutineDispatcher

    @Before
    fun setup() {
        elevatorRepository = ElevatorRepository()
        testDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testDispatcher) // Set the main dispatcher to the test dispatcher
    }

    @Test
    fun testElevatorMovesToFloor2() = runBlockingTest {
        // Request elevator to move to floor 2
        val elevatorStateFlow = elevatorRepository.selectDestination(2)

        val results = mutableListOf<ElevatorState>()
        elevatorStateFlow.collect { state ->
            results.add(state)
        }

        // Validate that elevator moves to floor 2 and door opens
        assertTrue(results.any { it.currentFloor == 2 && it.doorState == DoorState.OPEN })
    }

    @Test
    fun testElevatorMovesToFloor5() = runBlockingTest {
        // Request elevator to move to floor 5
        val elevatorStateFlow = elevatorRepository.selectDestination(5)

        val results = mutableListOf<ElevatorState>()
        elevatorStateFlow.collect { state ->
            results.add(state)
        }

        // Validate that elevator moves to floor 5 and door opens
        assertTrue(results.any { it.currentFloor == 5 && it.doorState == DoorState.OPEN })
    }

    @Test
    fun testElevatorMovesToFloor8() = runBlockingTest {
        // Request elevator to move to floor 8
        val elevatorStateFlow = elevatorRepository.selectDestination(8)

        val results = mutableListOf<ElevatorState>()
        elevatorStateFlow.collect { state ->
            results.add(state)
        }

        // Validate that elevator moves to floor 8 and door opens
        assertTrue(results.any { it.currentFloor == 8 && it.doorState == DoorState.OPEN })
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher to avoid affecting other tests
        testDispatcher.cleanupTestCoroutines() // Clean up the test dispatcher
    }
}