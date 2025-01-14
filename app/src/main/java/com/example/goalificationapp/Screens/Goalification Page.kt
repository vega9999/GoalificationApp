
package com.example.goalificationapp.Screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.goalificationapp.R

@Composable
fun GoalificationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomepageViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Goals",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(9) { index ->
                GoalItem(
                    item = viewModel.selectedItems[index],
                    isSelected = viewModel.waitingForApproval[index],
                    isWaitingForApproval = viewModel.waitingForApproval[index],
                    showBackground = viewModel.showBackground[index],
                    backgroundImage = R.drawable.lion_background,
                    index = index,
                    onClick = {
                        if (viewModel.selectedItems[index].isNotEmpty()) {
                            viewModel.toggleWaitingForApproval(index)
                            if (!viewModel.waitingForApproval[index]) {
                                viewModel.toggleShowBackground(index)
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun GoalItem(
    item: String,
    isSelected: Boolean,
    isWaitingForApproval: Boolean,
    showBackground: Boolean,
    backgroundImage: Int,
    index: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(
                when {
                    isWaitingForApproval -> Color.Yellow
                    isSelected -> Color.Green
                    item.isNotEmpty() -> Color(0xFFFFCC80)
                    else -> Color.LightGray
                }
            )
            .clickable(onClick = onClick)
    ) {
        if (showBackground) {
            Image(
                painter = painterResource(id = backgroundImage),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = when (index) {
                    0 -> Alignment.TopStart
                    1 -> Alignment.TopCenter
                    2 -> Alignment.TopEnd
                    3 -> Alignment.CenterStart
                    4 -> Alignment.Center
                    5 -> Alignment.CenterEnd
                    6 -> Alignment.BottomStart
                    7 -> Alignment.BottomCenter
                    8 -> Alignment.BottomEnd
                    else -> Alignment.Center
                }
            )
        }
        if (item.isNotEmpty()) {
            Text(
                text = item,
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.Center),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = if (showBackground) Color.White else Color.Black
            )
        }
        if (isWaitingForApproval) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black,
                    strokeWidth = 2.dp
                )
            }
        }
    }
}