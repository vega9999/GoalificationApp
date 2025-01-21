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
import androidx.navigation.NavController

@Composable
fun SelectGoalsTasksScreen(modifier: Modifier, navController: NavController, buttonIndex: Int) {
    val tasks = listOf(
        "Organize litter collection campaigns in parks",
        "Maintain a community garden",
        "Support blood donation events",
        "Run a soup kitchen for the needy",
        "Plant trees for an environmental project"
    )

    val goals = listOf(
        "Volunteer 10 hours per month",
        "Participate in 5 different projects",
        "Collect and recycle 100 kg of waste",
        "Plant 20 trees in one month",
        "Recruit 5 new volunteers for the project"
    )

    var selectedItem by remember { mutableStateOf("") }
    var isGoal by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Choose a task or a goal",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "Task",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(tasks) { task ->
                ItemCard(task, selectedItem == task && !isGoal) {
                    selectedItem = task
                    isGoal = false
                }
            }
        }

        Text(
            text = "Goal",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(goals) { goal ->
                ItemCard(goal, selectedItem == goal && isGoal) {
                    selectedItem = goal
                    isGoal = true
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (selectedItem.isNotEmpty()) {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedItem", selectedItem)
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("isGoal", isGoal)
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("buttonIndex", buttonIndex)
                    navController.popBackStack()
                }
            },
            enabled = selectedItem.isNotEmpty()
        ) {
            Text("Confirm selection")
        }
    }
}

@Composable
fun ItemCard(name: String, isSelected: Boolean, onSelect: () -> Unit) {
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
