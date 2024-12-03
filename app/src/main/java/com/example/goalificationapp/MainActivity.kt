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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.goalification.ui.theme.GoalificationAppTheme
import androidx.compose.ui.tooling.preview.Preview


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoalificationAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CustomAppBar(onMenuClick = { /* Handle navigation */ })
                    }
                ) { innerPadding ->
                    HomepageScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CustomAppBar(onMenuClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 8.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.Black
                )
            }
        }
    }
}

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
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
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
