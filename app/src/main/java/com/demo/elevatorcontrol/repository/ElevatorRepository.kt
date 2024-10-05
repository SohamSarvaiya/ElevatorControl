package com.demo.elevatorcontrol.repository

import com.demo.elevatorcontrol.Interface.ElevatorUseCase
import com.demo.elevatorcontrol.models.Direction
import com.demo.elevatorcontrol.models.DoorState
import com.demo.elevatorcontrol.models.ElevatorState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay

class ElevatorRepository : ElevatorUseCase {

    private var currentFloor: Int = 1
    private var isMoving: Boolean = false
 
    override suspend fun requestElevator(floor: Int): Flow<ElevatorState> = flow {
        isMoving = true
        emit(ElevatorState(isLoading = true))

        // Simulate elevator movement
        while (currentFloor != floor) {
            delay(1000)
            currentFloor = if (currentFloor < floor) currentFloor + 1 else currentFloor - 1
            emit(ElevatorState(currentFloor = currentFloor, direction = if (currentFloor < floor) Direction.UP else Direction.DOWN))
        }

        emit(ElevatorState(currentFloor = currentFloor, direction = Direction.IDLE, doorState = DoorState.OPEN))
        delay(2000) // Simulate doors open time
        emit(ElevatorState(currentFloor = currentFloor, direction = Direction.IDLE, doorState = DoorState.CLOSED))
        isMoving = false
    }

    override suspend fun selectDestination(floor: Int): Flow<ElevatorState> = requestElevator(floor)
}
