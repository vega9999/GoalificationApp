package com.example.goalificationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.goalification.ui.theme.GoalificationAppTheme
import androidx.compose.ui.unit.sp
import com.example.goalificationapp.Screens.CalendarScreen
import com.example.goalificationapp.Screens.HomepageScreen
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
    val currentScreen = remember { mutableStateOf("Homepage") }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(onMenuItemClick = { selectedScreen ->
                    currentScreen.value = selectedScreen
                    scope.launch { drawerState.close() } // Close the drawer after selection
                })
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
                when (currentScreen.value) {
                    "Homepage" -> HomepageScreen(
                        modifier = Modifier
                            .padding(paddingValues)
                    )

                    "Calendar" -> CalendarScreen(
                        modifier = Modifier
                            .padding(paddingValues)
                    )

                    else -> HomepageScreen(
                        modifier = Modifier
                            .padding(paddingValues)
                    )
                }
            }
        )
    }

}

@Composable
fun DrawerContent(onMenuItemClick: (String) -> Unit) {
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
            iconRes = R.drawable.ic_home,
            onClick = { onMenuItemClick("Homepage") }
        )

        DrawerMenuItem(
            text = stringResource(id = R.string.calendar_menu_item),
            iconRes = R.drawable.ic_calendar_black,
            onClick = { onMenuItemClick("Calendar") }
        )

        DrawerMenuItem(
            text = stringResource(id = R.string.stats_menu_item),
            iconRes = R.drawable.ic_stats,
            onClick = { onMenuItemClick("Stats") }
        )

        DrawerMenuItem(
            text = stringResource(id = R.string.stats_friends_menu_item),
            iconRes = R.drawable.ic_friends,
            onClick = { onMenuItemClick("Friends") }
        )
        DrawerMenuItem(
            text = stringResource(id = R.string.goalification_menu_item),
            iconRes = R.drawable.ic_goal,
            onClick = { onMenuItemClick("Goalification") }
        )

        DrawerMenuItem(
            text = stringResource(id = R.string.work_menu_item),
            iconRes = R.drawable.ic_work,
            onClick = { onMenuItemClick("Work") }
        )

        DrawerMenuItem(
            text = stringResource(id = R.string.freetime_menu_item),
            iconRes = R.drawable.ic_freetime,
            onClick = { onMenuItemClick("Freetime") }
        )
    }
}

@Composable
fun DrawerMenuItem(text: String, iconRes: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.drawer_menu_item_padding))
            .wrapContentHeight()
            .clickable { onClick() },
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

