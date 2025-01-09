
package com.example.goalificationapp.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SelectGoalsTasksScreen(modifier: Modifier, navController: NavController) {
    val tasks = listOf(
        "Müllsammelaktion in Parks organisieren",
        "Gemeinschaftsgarten pflegen",
        "Blutspendeveranstaltung unterstützen",
        "Suppenküche für Bedürftige betreuen",
        "Bäume pflanzen für ein Umweltprojekt",
        "Tiere in einem Tierheim versorgen",
        "Sportveranstaltung für Kinder organisieren",
        "Mentoring für Jugendliche übernehmen",
        "Senioren im Umgang mit Technik helfen",
        "Sprachkurse für Flüchtlinge anbieten",
        "Barrierefreie Zugänge verbessern helfen",
        "Bücher in einer Bibliothek sortieren",
        "Spielnachmittage für Kinder im Krankenhaus organisieren",
        "Fundraising-Veranstaltung planen",
        "Workshops für gesunde Ernährung halten"
    )

    val goals = listOf(
        "10 Stunden pro Monat Freiwilligenarbeit leisten",
        "An 5 verschiedenen Projekten teilnehmen",
        "100 kg Müll sammeln und recyceln",
        "20 Bäume in einem Monat pflanzen",
        "5 neue Freiwillige für das Projekt gewinnen",
        "Mindestens 50 Mahlzeiten verteilen",
        "100 Bücher in einer Bibliothek sortieren",
        "15 Senioren beim Umgang mit Technik helfen",
        "3 erfolgreiche Workshops organisieren",
        "Mindestens 3 Gemeinschaftsveranstaltungen leiten",
        "10 Tiere in einem Monat betreuen",
        "2 Mentoring-Sitzungen pro Woche anbieten",
        "5 Flüchtlingen bei der Integration helfen",
        "Mindestens 20 Kinder bei Veranstaltungen betreuen",
        "3 Blutspendeaktionen unterstützen"
    )

    var selectedTask by remember { mutableStateOf("") }
    var selectedGoal by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select Your Task",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(tasks) { task ->
                TaskItem(task, selectedTask == task) {
                    selectedTask = task
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Select Your Goal",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(goals) { goal ->
                TaskItem(goal, selectedGoal == goal) {
                    selectedGoal = goal
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (selectedTask.isNotEmpty() && selectedGoal.isNotEmpty()) {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedTask", selectedTask)
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedGoal", selectedGoal)
                    navController.popBackStack()
                }
            },
            enabled = selectedTask.isNotEmpty() && selectedGoal.isNotEmpty()
        ) {
            Text("Confirm Selection")
        }
    }
}

@Composable
fun TaskItem(name: String, isSelected: Boolean, onSelect: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFFFF9C4) else Color.White,
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onSelect() }
    ) {
        Text(
            text = name,
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSelectGoalsTasksScreen() {
    SelectGoalsTasksScreen(
        modifier = Modifier.fillMaxSize(),
        navController = rememberNavController()
    )
}
