package com.example.nove

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nove.ui.theme.NoveTheme


data class Workout(
    val id: Int,
    val title: String,
    val day: String,
    val level: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoveTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FitPlannerApp()
                }
            }
        }
    }
}

@Composable
fun FitPlannerApp() {

    var workouts by remember { mutableStateOf(emptyList<Workout>()) }

    var title by remember { mutableStateOf("") }
    var day by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "FitPlanner: Agenda de Treinos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))


        WorkoutForm(
            title = title,
            onTitleChange = { title = it },
            day = day,
            onDayChange = { day = it },
            level = level,
            onLevelChange = { level = it },
            onAddWorkout = {
                if (title.isNotBlank() && day.isNotBlank() && level.isNotBlank()) {
                    val newId = workouts.size + 1
                    val newWorkout = Workout(newId, title, day, level)


                    workouts = workouts + newWorkout


                    title = ""
                    day = ""
                    level = ""
                }
            }
        )

        Spacer(Modifier.height(24.dp))

        Text("Meus Treinos", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        WorkoutGrid(workouts = workouts)
    }
}

@Composable
fun WorkoutForm(
    title: String,
    onTitleChange: (String) -> Unit,
    day: String,
    onDayChange: (String) -> Unit,
    level: String,
    onLevelChange: (String) -> Unit,
    onAddWorkout: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        TextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text("Nome do Treino (ex: Corrida)") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = day,
            onValueChange = onDayChange,
            label = { Text("Dia (ex: Segunda)") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = level,
            onValueChange = onLevelChange,
            label = { Text("Nível (ex: Iniciante)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = onAddWorkout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar Treino")
        }
    }
}

@Composable
fun WorkoutGrid(workouts: List<Workout>) {
    LazyVerticalGrid(

        columns = GridCells.Fixed(2),

        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(workouts, key = { it.id }) { workout ->
            WorkoutCard(workout = workout)
        }
    }
}
@Composable
fun WorkoutCard(workout: Workout) {
    Box(
        modifier = Modifier
            .size(width = 150.dp, height = 120.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = workout.day,
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = workout.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = workout.level,
                fontSize = 14.sp,
                modifier = Modifier
                    .background(Color.DarkGray, RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                color = Color.White
            )
        }
    }
}