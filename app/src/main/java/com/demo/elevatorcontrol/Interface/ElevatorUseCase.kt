package com.demo.elevatorcontrol.Interface

import com.demo.elevatorcontrol.models.ElevatorState
import kotlinx.coroutines.flow.Flow

interface ElevatorUseCase {
    suspend fun requestElevator(floor: Int): Flow<ElevatorState>
    suspend fun selectDestination(floor: Int): Flow<ElevatorState>
}
