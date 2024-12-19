package com.example.goalificationapp.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.goalificationapp.R

@SuppressLint("NewApi")
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier
) {
    val currentDate = remember { mutableStateOf(LocalDate.now()) }
    var selectedDay by remember { mutableStateOf(currentDate.value) }

    val currentWeek = remember(selectedDay) { generateWeekDays(selectedDay) }

    val dailyTasks = remember { mutableStateListOf("Answer emails", "Drink water", "Write reports") }
    val weeklyTasks = remember { mutableStateListOf("Have meeting") }

    Column(modifier = Modifier.padding(16.dp)) {
        CalendarHeader(
            currentDate = currentDate.value,
            onWeekChange = { newDate ->
                selectedDay = newDate
            }
        )

        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(currentWeek) { day ->
                DayItem(
                    day = day,
                    isSelected = day == selectedDay,
                    isToday = day == LocalDate.now()
                ) {
                    selectedDay = day
                }
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_medium_2)))

        Text(
            stringResource(id = R.string.daily_label),
            fontSize = dimensionResource(id = R.dimen.text_regular_2x).value.sp,
            color = colorResource(id = R.color.secondary_text)
        )
        TaskList(tasks = dailyTasks, type = "daily")

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_medium_2)))

        Text(
            stringResource(id = R.string.weekly_label),
            fontSize = dimensionResource(id = R.dimen.text_regular_2x).value.sp,
            color = colorResource(id = R.color.secondary_text)
        )
        TaskList(tasks = weeklyTasks, type = "weekly")
    }
}

@SuppressLint("NewApi")
@Composable
fun CalendarHeader(currentDate: LocalDate, onWeekChange: (LocalDate) -> Unit) {
    val formatter = java.time.format.DateTimeFormatter.ofPattern("MMMM yyyy")
    val currentMonthYear = currentDate.format(formatter)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(id = R.dimen.margin_small)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { onWeekChange(currentDate.minusWeeks(1)) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = "Previous Week"
            )
        }

        Text(
            text = currentMonthYear,
            fontSize = dimensionResource(id = R.dimen.text_regular_3x).value.sp,
            color = colorResource(id = R.color.primary_text)
        )

        IconButton(onClick = { onWeekChange(currentDate.plusWeeks(1)) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "Next Week"
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        daysOfWeek.forEach { day ->
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                fontSize = dimensionResource(id = R.dimen.text_regular).value.sp,
                color = colorResource(id = R.color.secondary_text),
                textAlign = TextAlign.Center
            )
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun DayItem(day: LocalDate, isSelected: Boolean, isToday: Boolean, onClick: () -> Unit) {
    val backgroundColor = when {
        isSelected -> Color.Blue
        isToday -> Color.LightGray
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.margin_medium))
            .clickable(onClick = onClick)
            .background(backgroundColor)
            .padding(8.dp)
    ) {
        Text(
            text = day.dayOfMonth.toString(),
            fontSize = dimensionResource(id = R.dimen.text_regular_2x).value.sp,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Composable
fun TaskList(tasks: List<String>, type: String) {
    tasks.forEach { task ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.margin_small)),
            elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.margin_small))
        ) {
            Row(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.margin_medium_2))
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(task)
                Text("0/3")
            }
        }
    }
}

@SuppressLint("NewApi")
fun generateWeekDays(date: LocalDate): List<LocalDate> {
    val startOfWeek = date.with(java.time.DayOfWeek.MONDAY)
    return (0..6).map { startOfWeek.plusDays(it.toLong()) }
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    MaterialTheme {
        CalendarScreen()
    }
}
