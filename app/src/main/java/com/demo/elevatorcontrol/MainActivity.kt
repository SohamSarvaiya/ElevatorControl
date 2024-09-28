package com.demo.elevatorcontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import com.demo.elevatorcontrol.repository.ElevatorRepository
import com.demo.elevatorcontrol.screens.ElevatorControlScreen
import com.demo.elevatorcontrol.ui.theme.ElevatorControlTheme
import com.demo.elevatorcontrol.viewmodels.ElevatorViewModel
import com.demo.elevatorcontrol.viewmodels.ElevatorViewModelFactory

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Set up ViewModel with repository
        val repository = ElevatorRepository()
        val viewModelFactory = ElevatorViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[ElevatorViewModel::class.java]


        setContent {
            ElevatorControlTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = "Elevator Control", color = Color.White)
                                },
                            )
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding).background(color = Color.White)) {

                            ElevatorControlScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }

    }
}

