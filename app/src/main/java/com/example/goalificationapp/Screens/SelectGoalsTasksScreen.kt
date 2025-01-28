package com.example.goalificationapp.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.goalificationapp.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SelectGoalsTasksScreen(modifier: Modifier, navController: NavController, buttonIndex: Int) {
    var showAddDialog by remember { mutableStateOf(false) }
    var tasks by remember { mutableStateOf(listOf(
        "Organize litter collection campaigns in parks",
        "Maintain a community garden",
        "Support blood donation events",
        "Run a soup kitchen for the needy",
        "Plant trees for an environmental project"
    )) }

    var goals by remember { mutableStateOf(listOf(
        "Volunteer 10 hours per month",
        "Participate in 5 different projects",
        "Collect and recycle 100 kg of waste",
        "Plant 20 trees in one month",
        "Recruit 5 new volunteers for the project"
    )) }

    var selectedItem by remember { mutableStateOf("") }
    var isGoal by remember { mutableStateOf(false) }

    if (showAddDialog) {
        AddTaskGoalDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { text, isGoalItem ->
                if (isGoalItem) {
                    goals = goals + text
                } else {
                    tasks = tasks + text
                }
                showAddDialog = false
            }
        )
    }

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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { showAddDialog = true },
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primary)
                )
            ) {
                Text("Add Task/Goal", color = Color.Black)
            }

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
                enabled = selectedItem.isNotEmpty(),
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primary)
                )
            ) {
                Text("Confirm selection", color = Color.Black)
            }
        }
    }
}

@Composable
fun AddTaskGoalDialog(
    onDismiss: () -> Unit,
    onAdd: (String, Boolean) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var isGoal by remember { mutableStateOf(false) }
    val primaryColor = colorResource(id = R.color.primary)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add New Task/Goal",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text(if (isGoal) "Goal" else "Task") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primaryColor,
                        focusedLabelColor = colorResource(R.color.black),
                        cursorColor = primaryColor
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = !isGoal,
                            onClick = { isGoal = false },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = primaryColor
                            )
                        )
                        Text("Task")
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isGoal,
                            onClick = { isGoal = true },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = primaryColor
                            )
                        )
                        Text("Goal")
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (text.isNotEmpty()) {
                        onAdd(text, isGoal)
                    }
                },
                enabled = text.isNotEmpty(),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = primaryColor
                )
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = primaryColor
                )
            ) {
                Text("Cancel")
            }
        },
        containerColor = colorResource(R.color.bg_color)
    )
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
