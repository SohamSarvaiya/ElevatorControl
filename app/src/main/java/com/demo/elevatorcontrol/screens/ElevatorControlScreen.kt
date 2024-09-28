package com.demo.elevatorcontrol.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.elevatorcontrol.R
import com.demo.elevatorcontrol.models.Direction
import com.demo.elevatorcontrol.models.DoorState
import com.demo.elevatorcontrol.models.ElevatorState
import com.demo.elevatorcontrol.ui.theme.Pink40
import com.demo.elevatorcontrol.ui.theme.PinkDark40
import com.demo.elevatorcontrol.ui.theme.Purple80
import com.demo.elevatorcontrol.ui.theme.PurpleGrey40
import com.demo.elevatorcontrol.ui.theme.orange
import com.demo.elevatorcontrol.viewmodels.ElevatorViewModel

@Composable
fun ElevatorControlScreen(viewModel: ElevatorViewModel) {
    val elevatorState by viewModel.elevatorState.collectAsState()

    // State to track the selected floor (default to 1)
    var selectedFloor by remember { mutableStateOf(1) } // Change this to 1 to start with the first floor

    // Define arrow icons (replace with your drawable resources)
    val upArrow = painterResource(id = R.drawable.up_arrow) // Add your up arrow image in drawable
    val downArrow = painterResource(id = R.drawable.down_arrow) // Add your down arrow image in drawable

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display current floor and door status
        CurrentFloorDisplay(elevatorState)

        Spacer(modifier = Modifier.height(20.dp))

        // Display live Direction status
        DirectionButtons(elevatorState, upArrow, downArrow)

        Spacer(modifier = Modifier.height(40.dp))

        // Floor selection grid
        FloorSelectionGrid(viewModel, selectedFloor) { selectedFloor = it }

        Spacer(modifier = Modifier.height(16.dp))

        // Spinner to indicate loading state
        if (elevatorState.isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun CurrentFloorDisplay(elevatorState: ElevatorState) {
    val currentFloorDisplay = when {
        elevatorState.isLoading -> "" // Show empty string during loading
        elevatorState.currentFloor == 0 -> "G" // Show "G" for ground floor
        else -> elevatorState.currentFloor.toString() // Show the current floor number
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(color = PurpleGrey40, shape = RoundedCornerShape(10.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Current Floor: $currentFloorDisplay",
                fontSize = 17.sp,
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(
                    color = if (elevatorState.doorState == DoorState.OPEN) PinkDark40 else Pink40,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = "Doors: ${elevatorState.doorState}",
                fontSize = 17.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun DirectionButtons(elevatorState: ElevatorState, upArrow: Painter, downArrow: Painter) {
    Row(
        modifier = Modifier
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Request elevator with up and down arrows
        Box(
            modifier = Modifier
                .background(Color.Black, shape = RoundedCornerShape(10.dp)) // Rounded corners
                .padding(3.dp)
        ) {
            Column {
                // Up Arrow Button
                Image(
                    painter = upArrow,
                    contentDescription = "Call Elevator Up",
                    modifier = Modifier
                        .size(45.dp)
                        .background(
                            when (elevatorState.direction) {
                                Direction.UP -> orange // Change to orange when moving up
                                Direction.DOWN -> PurpleGrey40 // Default color when not moving up
                                Direction.IDLE -> PurpleGrey40 // Default color when idle
                            }, shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                        )
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Down Arrow Button
                Image(
                    painter = downArrow,
                    contentDescription = "Call Elevator Down",
                    modifier = Modifier
                        .size(45.dp)
                        .background(
                            when (elevatorState.direction) {
                                Direction.DOWN -> orange // Change to orange when moving down
                                Direction.UP -> PurpleGrey40 // Default color when not moving down
                                Direction.IDLE -> PurpleGrey40 // Default color when idle
                            }, shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                        )
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun FloorSelectionGrid(viewModel: ElevatorViewModel, selectedFloor: Int, onSelectFloor: (Int) -> Unit) {
    val floors = listOf("10", "9", "8", "7", "6", "5", "4", "3", "2", "1", "G") // List of floors

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(floors.size) { index -> // Dynamically get the size from the list
            val floor = floors[index] // Get the floor from the list
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(50.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(
                        color = if (selectedFloor == if (floor == "G") 0 else floor.toInt()) orange else Color.LightGray // Change color if selected
                    )
                    .border(1.dp, color = PurpleGrey40, RoundedCornerShape(5.dp))
                    .clickable { // Handle click event
                        val newSelectedFloor = if (floor == "G") 0 else floor.toInt()
                        onSelectFloor(newSelectedFloor)
                        viewModel.selectDestination(newSelectedFloor)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(floor, fontSize = 18.sp, color = Color.Black)
            }
        }
    }
}


