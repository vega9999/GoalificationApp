package com.example.goalificationapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.goalification.ui.theme.GoalificationAppTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoalificationAppTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent()
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu Icon")
                        }
                    }
                )
            },
            content = { paddingValues ->
                HomepageScreen(
                    modifier = Modifier
                        .padding(paddingValues)
                )
            }
        )
    }

}

@Composable
fun DrawerContent() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(dimensionResource(id = R.dimen.navigation_drawer_width))
            .padding(dimensionResource(id = R.dimen.margin_medium_2))
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = dimensionResource(id = R.dimen.text_heading_1x).value.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.margin_large))
                .align(Alignment.Start),
            style = MaterialTheme.typography.titleLarge
        )

        DrawerMenuItem(
            text = stringResource(id = R.string.home_page_menu_item),
            iconRes = R.drawable.ic_home
        )

        DrawerMenuItem(
            text = stringResource(id = R.string.calendar_menu_item),
            iconRes = R.drawable.ic_calendar_black
        )
        DrawerMenuItem(
            text = stringResource(id = R.string.stats_menu_item),
            iconRes = R.drawable.ic_stats
        )
        DrawerMenuItem(
            text = stringResource(id = R.string.stats_friends_menu_item),
            iconRes = R.drawable.ic_friends
        )
        DrawerMenuItem(
            text = stringResource(id = R.string.goalification_menu_item),
            iconRes = R.drawable.ic_goal
        )
        DrawerMenuItem(
            text = stringResource(id = R.string.work_menu_item),
            iconRes = R.drawable.ic_work
        )
        DrawerMenuItem(
            text = stringResource(id = R.string.freetime_menu_item),
            iconRes = R.drawable.ic_freetime
        )
    }
}

@Composable
fun DrawerMenuItem(text: String, iconRes: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.drawer_menu_item_padding))
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            tint = colorResource(id = R.color.icons),
            modifier = Modifier.size(dimensionResource(id = R.dimen.margin_large))
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.margin_medium_2)))
        Text(
            text = text,
            fontSize = dimensionResource(id = R.dimen.text_regular_2x).value.sp,
            color = colorResource(id = R.color.primary_text),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@SuppressLint("ResourceAsColor")
@Composable
fun HomepageScreen(modifier: Modifier = Modifier) {

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
        GoalsGrid()
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

@SuppressLint("ResourceAsColor")
@Composable
fun GoalsGrid() {
    val goals = listOf(
        "reports writing 1/3",
        "Test",
        "+"
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.margin_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.margin_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.margin_medium))
    ) {
        items(goals) { goal ->
            Box(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.goals_grid_size))
                    .background(
                        color = colorResource(id = R.color.primary),
                        RoundedCornerShape(dimensionResource(id = R.dimen.margin_medium))
                    )
                    .padding(dimensionResource(id = R.dimen.margin_medium)),
                contentAlignment = Alignment.Center
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
