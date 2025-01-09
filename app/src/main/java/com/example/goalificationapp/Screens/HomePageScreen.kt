package com.example.goalificationapp.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.goalification.ui.theme.GoalificationAppTheme
import com.example.goalificationapp.R
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@SuppressLint("ResourceAsColor")
@Composable
fun HomepageScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.bg_color))
            .padding(dimensionResource(id = R.dimen.margin_medium_2))
    ) {
        // Work and Freetime Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconWithLabel(
                iconRes = R.drawable.ic_work,
                label = stringResource(id = R.string.work_label),
                backgroundColor = colorResource(id = R.color.primary)
            )
            IconWithLabel(
                iconRes = R.drawable.ic_freetime,
                label = stringResource(id = R.string.freetime_label),
                backgroundColor = colorResource(id = R.color.secondary)
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_large)))

        // Challenges Section
        SectionTitle(stringResource(id = R.string.challenges_label))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_medium)))
        ChallengeCard(
            title = "Write reports",
            subtitle = "Employer xxx",
            progress = "1/3"
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_large)))

        // Notes Section
        SectionTitle(stringResource(id = R.string.notes_label))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_medium)))
        NoteCard("Last day before vacation")

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_large)))

        // Goals Section
        SectionTitle(stringResource(id = R.string.goals_label))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_medium)))
        GoalsGrid(navController = navController)
    }
}


@Composable
fun IconWithLabel(iconRes: Int, label: String, backgroundColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.icon_with_label_box_size))
                .background(backgroundColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_with_label_icon_size))
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_medium)))
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start
    )
}

@Composable
fun ChallengeCard(title: String, subtitle: String, progress: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                colorResource(id = R.color.challenge_card_color),
                RoundedCornerShape(dimensionResource(R.dimen.margin_medium))
            )
            .padding(dimensionResource(id = R.dimen.margin_medium_2)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = "Note Icon",
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_with_label_icon_size))
        )
        Column {
            Text(text = title, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.secondary_text)
            )
        }
        Text(text = progress, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun NoteCard(note: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                colorResource(id = R.color.challenge_card_color),
                RoundedCornerShape(dimensionResource(id = R.dimen.margin_medium))
            )
            .padding(dimensionResource(id = R.dimen.margin_medium_2)),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_note),
            contentDescription = "Note Icon",
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_with_label_icon_size))
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.margin_medium_2)))
        Text(text = note, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun GoalsGrid(navController: NavController) {
    val goals = listOf("+", "+", "+")

    val buttonBackgroundColor = colorResource(id = R.color.primary)

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.margin_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.margin_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.margin_medium))
    ) {
        items(goals) { goal ->
            Button(
                onClick = {
                    navController.navigate("selectGoalsTasksScreen")
                },
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.goals_grid_size))
                    .padding(dimensionResource(id = R.dimen.margin_medium)),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.margin_medium)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonBackgroundColor
                )
            ) {
                Text(
                    text = goal,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = dimensionResource(id = R.dimen.text_regular).value.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoalificationAppTheme {
        HomepageScreen(modifier = Modifier.fillMaxSize())
    }
}