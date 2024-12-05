package com.example.goalificationapp

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
import androidx.compose.material3.DrawerState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
                    title = { Text("Goalification App") },
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
                HomepageScreen(modifier = Modifier.padding(paddingValues))
            }
        )
    }

}

@Composable
fun DrawerContent() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
            .padding(16.dp)
    ) {
        Text(
            text = "Goalification",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .align(Alignment.Start),
            style = MaterialTheme.typography.titleLarge
        )

        DrawerMenuItem(
            text = "Homepage",
            iconRes = R.drawable.ic_home // Replace with your actual resource
        )

        DrawerMenuItem(
            text = "Calendar",
            iconRes = R.drawable.ic_calendar_black // Replace with your actual resource
        )
        DrawerMenuItem(
            text = "Stats",
            iconRes = R.drawable.ic_stats // Replace with your actual resource
        )
        DrawerMenuItem(
            text = "Stats-Friends",
            iconRes = R.drawable.ic_friends // Replace with your actual resource
        )
        DrawerMenuItem(
            text = "Goalification",
            iconRes = R.drawable.ic_goal // Replace with your actual resource
        )
        DrawerMenuItem(
            text = "Work",
            iconRes = R.drawable.ic_work // Replace with your actual resource
        )
        DrawerMenuItem(
            text = "Freetime",
            iconRes = R.drawable.ic_freetime // Replace with your actual resource
        )
    }
}

@Composable
fun DrawerMenuItem(text: String, iconRes: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp)) // Space between icon and text
        Text(
            text = text,
            fontSize = 18.sp,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomepageScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Work and Freetime Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconWithLabel(
                iconRes = R.drawable.ic_work,
                label = "Work",
                backgroundColor = Color(0xFFFFCC80)
            )
            IconWithLabel(
                iconRes = R.drawable.ic_freetime,
                label = "Freetime",
                backgroundColor = Color(0xFFFFF59D)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Challenges Section
        SectionTitle("Challenges")
        Spacer(modifier = Modifier.height(8.dp))
        ChallengeCard(
            title = "Write reports",
            subtitle = "Employer xxx",
            progress = "1/3"
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Notes Section
        SectionTitle("Notes")
        Spacer(modifier = Modifier.height(8.dp))
        NoteCard("Last day before vacation")

        Spacer(modifier = Modifier.height(24.dp))

        // Goals Section
        SectionTitle("Goals")
        Spacer(modifier = Modifier.height(8.dp))
        GoalsGrid()
    }


}

@Composable
fun IconWithLabel(iconRes: Int, label: String, backgroundColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(backgroundColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
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
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = "Note Icon",
            modifier = Modifier.size(40.dp)
        )
        Column {
            Text(text = title, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
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
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_note),
            contentDescription = "Note Icon",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = note, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun GoalsGrid() {
    val goals = listOf(
        "reports writing 1/3",
        "Test",
        "Test",
        "Test",
        "Test",
        "Test",
        "Test",
        "Test",
        "Test"
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(goals) { goal ->
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(color = Color(0xFFFFCC80), RoundedCornerShape(8.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = goal,
                    style = MaterialTheme.typography.bodySmall,
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
