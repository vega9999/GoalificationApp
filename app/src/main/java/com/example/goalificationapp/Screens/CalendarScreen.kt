package com.example.goalificationapp.Screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.goalificationapp.R
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import java.time.format.TextStyle
import java.util.Locale

@SuppressLint("NewApi")
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier
) {
    val currentDate = remember { mutableStateOf(LocalDate.now()) }
    val daysInMonth = remember(currentDate.value) { generateDaysOfMonth(currentDate.value) }

    var selectedDay by remember { mutableStateOf(currentDate.value) }
    val dailyTasks =
        remember { mutableStateListOf("Answer emails", "Drink water", "Write reports") }
    val weeklyTasks = remember { mutableStateListOf("Have meeting") }

    val currentWeekday = currentDate.value.dayOfWeek.value - 1 // Montag = 0, Sonntag = 6

    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = currentWeekday)

    Column(modifier = Modifier.padding(16.dp)) {
        CalendarHeader(
            currentDate = currentDate.value,
            onMonthChange = { newDate ->
                currentDate.value = newDate
            }
        )

        LazyRow(
            state = lazyListState,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(daysInMonth) { day ->
                Column(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.margin_small)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                        fontSize = dimensionResource(id = R.dimen.text_regular).value.sp,
                        color = colorResource(id = R.color.secondary_text),
                        textAlign = TextAlign.Center
                    )

                    DayItem(day = day, isSelected = day == selectedDay) {
                        selectedDay = day
                    }
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
fun CalendarHeader(currentDate: LocalDate, onMonthChange: (LocalDate) -> Unit) {
    val currentMonth = currentDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val currentYear = currentDate.year

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(id = R.dimen.margin_small)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { onMonthChange(currentDate.minusMonths(1)) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = "Previous Month"
            )
        }

        Text(
            text = "$currentMonth $currentYear",
            fontSize = dimensionResource(id = R.dimen.text_regular_3x).value.sp,
            color = colorResource(id = R.color.primary_text)
        )

        IconButton(onClick = { onMonthChange(currentDate.plusMonths(1)) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "Next Month"
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val daysOfWeek = listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")
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
fun DayItem(day: LocalDate, isSelected: Boolean, onClick: () -> Unit) {
    Text(
        text = day.dayOfMonth.toString(),
        fontSize = dimensionResource(id = R.dimen.text_regular_2x).value.sp,
        color = if (isSelected) Color.Blue else Color.Black,
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.margin_medium))
            .clickable(onClick = onClick)
    )
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
fun generateDaysOfMonth(date: LocalDate): List<LocalDate> {
    val start = date.withDayOfMonth(1)
    val end = date.withDayOfMonth(date.lengthOfMonth())
    return (0 until end.dayOfMonth).map { start.plusDays(it.toLong()) }
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    MaterialTheme {
        CalendarScreen(
            modifier = Modifier.fillMaxSize(),
        )
    }
}