package com.demo.elevatorcontrol.models

data class ElevatorState(
    val currentFloor: Int = 1,
    val direction: Direction = Direction.IDLE,
    val doorState: DoorState = DoorState.CLOSED,
    val isLoading: Boolean = false
)

enum class Direction {
    UP, DOWN, IDLE
}

enum class DoorState {
    OPEN, CLOSED
}
