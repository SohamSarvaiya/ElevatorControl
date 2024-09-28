package com.demo.elevatorcontrol.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.elevatorcontrol.Interface.ElevatorUseCase

class ElevatorViewModelFactory(private val useCase: ElevatorUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ElevatorViewModel::class.java)) {
            return ElevatorViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
