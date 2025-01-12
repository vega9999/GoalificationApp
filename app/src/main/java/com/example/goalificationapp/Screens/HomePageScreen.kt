package com.example.goalificationapp.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.goalificationapp.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class HomepageViewModel : ViewModel() {
    private val _selectedItems = mutableStateListOf<String>("", "", "")
    val selectedItems: List<String> = _selectedItems

    private val _isGoals = mutableStateListOf<Boolean>(false, false, false)
    val isGoals: List<Boolean> = _isGoals

    fun updateItem(index: Int, item: String, isGoal: Boolean) {
        _selectedItems[index] = item
        _isGoals[index] = isGoal
    }
}

@SuppressLint("ResourceAsColor")
@Composable
fun HomepageScreen(modifier: Modifier = Modifier, navController: NavController, viewModel: HomepageViewModel = viewModel()) {
    val selectedItems by remember { mutableStateOf(viewModel.selectedItems) }
    val isGoals by remember { mutableStateOf(viewModel.isGoals) }

    LaunchedEffect(navController) {
        navController.currentBackStackEntry?.savedStateHandle?.apply {
            getLiveData<String>("selectedItem").observe(navController.currentBackStackEntry!!) { item ->
                getLiveData<Int>("buttonIndex").observe(navController.currentBackStackEntry!!) { index ->
                    getLiveData<Boolean>("isGoal").observe(navController.currentBackStackEntry!!) { isGoal ->
                        if (item != null && index != null && isGoal != null) {
                            viewModel.updateItem(index, item, isGoal)
                        }
                    }
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.bg_color))
            .padding(dimensionResource(id = R.dimen.margin_medium_2))
    ) {
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

        SectionTitle(stringResource(id = R.string.challenges_label))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_medium)))
        ChallengeCard(
            title = "Write reports",
            subtitle = "Employer xxx",
            progress = "1/3"
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_large)))

        SectionTitle(stringResource(id = R.string.notes_label))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_medium)))
        NoteCard("Last day before vacation")

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_large)))

        SectionTitle(stringResource(id = R.string.goals_label))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_medium)))
        GoalsGrid(
            navController = navController,
            selectedItems = selectedItems,
            isGoals = isGoals
        )
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
            contentDescription = "Challenge Icon",
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
fun GoalsGrid(
    navController: NavController,
    selectedItems: List<String>,
    isGoals: List<Boolean>
) {
    val buttonBackgroundColor = colorResource(id = R.color.primary)

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.margin_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.margin_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.margin_medium))
    ) {
        items(3) { index ->
            if (selectedItems[index].isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = buttonBackgroundColor,
                    ),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.margin_medium)),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.goals_grid_size))
                        .padding(dimensionResource(id = R.dimen.margin_medium))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isGoals[index]) "Ziel:" else "Aufgabe:",
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = selectedItems[index],
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                Button(
                    onClick = {
                        navController.navigate("selectGoalsTasksScreen/$index")
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
                        text = "+",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = dimensionResource(id = R.dimen.text_regular).value.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}