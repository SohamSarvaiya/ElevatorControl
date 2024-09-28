package com.demo.elevatorcontrol.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.elevatorcontrol.Interface.ElevatorUseCase
import com.demo.elevatorcontrol.models.ElevatorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ElevatorViewModel(private val useCase: ElevatorUseCase) : ViewModel() {

    private val _elevatorState = MutableStateFlow(ElevatorState())
    val elevatorState: StateFlow<ElevatorState> = _elevatorState

    fun requestElevator(floor: Int) {
        viewModelScope.launch {
            useCase.requestElevator(floor).collect { state ->
                _elevatorState.value = state
            }
        }
    }

    fun selectDestination(floor: Int) {
        viewModelScope.launch {
            useCase.selectDestination(floor).collect { state ->
                _elevatorState.value = state
            }
        }
    }
}
