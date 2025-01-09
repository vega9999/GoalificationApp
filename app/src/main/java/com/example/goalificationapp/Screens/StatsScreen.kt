package com.example.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen() {
    val activity1Description = remember { mutableStateOf("1x write report") }
    val activity2Description = remember { mutableStateOf("1x meeting") }
    val selectedTab = remember { mutableStateOf("Week") }

    Column(modifier = Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.height(16.dp))

        // Tabs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TabButton(
                text = "12 Weeks",
                isSelected = selectedTab.value == "12 Weeks",
                onClick = { selectedTab.value = "12 Weeks" }
            )
            TabButton(
                text = "Month",
                isSelected = selectedTab.value == "Month",
                onClick = { selectedTab.value = "Month" }
            )
            TabButton(
                text = "Week",
                isSelected = selectedTab.value == "Week",
                onClick = { selectedTab.value = "Week" }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Chart section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFC17A))
                .padding(vertical = 16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "November",
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    BarColumn(label = "Mo", height = 2.dp)
                    BarColumn(label = "Tu", height = 6.dp)
                    BarColumn(label = "We", height = 3.dp)
                    BarColumn(label = "Th", height = 4.dp)
                    BarColumn(label = "Fr", height = 5.dp)
                    BarColumn(label = "Sa", height = 0.dp)
                    BarColumn(label = "Su", height = 0.dp)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Goal Streak section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE9C46A)) // Background color here
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Goal Streak",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = "15x",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Activities section
        Text(
            text = "Activities:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        ActivityItem(
            time = "16.11.2024 14:11",
            description = activity1Description.value,
            onDescriptionChange = { activity1Description.value = it }
        )

        ActivityItem(
            time = "16.11.2024 12:20",
            description = activity2Description.value,
            onDescriptionChange = { activity2Description.value = it }
        )
    }
}

@Composable
fun TabButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFFFFC17A) else Color.White
        ),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .height(50.dp)
            .padding(4.dp)
    ) {
        Text(text = text, color = if (isSelected) Color.Black else Color.Gray)
    }
}

@Composable
fun BarColumn(label: String, height: Dp) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        //modifier = Modifier.height(100.dp)
    ) {
            Box(
                modifier = Modifier
                    .width(16.dp)
                    .height(height)
                    .background(Color.White)
            )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp)
    }
}

@Composable
fun ActivityItem(time: String, description: String, onDescriptionChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Time display
        Text(
            text = time,
            fontSize = 14.sp,
            color = Color.Gray
        )

        // Editable description
        TextField(
            value = description,
            onValueChange = { newDescription -> onDescriptionChange(newDescription) },
            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
            modifier = Modifier
                .width(150.dp)  // Set a fixed width for consistency
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
            singleLine = true, // Make sure the text doesn't wrap to the next line
        )
    }
}

