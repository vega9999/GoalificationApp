package com.example.goalificationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goalification.ui.theme.GoalificationAppTheme
import com.example.goalificationapp.Screens.CalendarScreen
import com.example.goalificationapp.Screens.HomepageScreen
import com.example.goalificationapp.Screens.LoginScreen
import com.example.goalificationapp.Screens.SelectGoalsTasksScreen
import com.example.goalificationapp.ui.theme.LoginViewModel
import com.example.stats.StatsScreen
import kotlinx.coroutines.launch
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.goalificationapp.Screens.EmailLoginScreen
import com.example.goalificationapp.Screens.ForgotPasswordScreen
import com.example.goalificationapp.Screens.GoalificationScreen
import com.example.goalificationapp.Screens.RegistrationScreen
import com.google.firebase.FirebaseApp
import java.util.prefs.Preferences

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            GoalificationAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: LoginViewModel = viewModel()) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val username = viewModel.username.collectAsState(initial = null)
    val isLoggedIn = viewModel.isLoggedIn.collectAsState(initial = false)

    val startDestination = if (isLoggedIn.value) "Homepage" else "login"

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    username = username.value ?: "Guest",
                    onMenuItemClick = { selectedScreen ->
                        scope.launch {
                            drawerState.close()
                            navController.navigate(selectedScreen)
                        }
                    },
                    onLogoutClick = {
                        scope.launch {
                            drawerState.close()
                            viewModel.logout()
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                )
            }
        },
        drawerState = drawerState
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
        ) {
            composable("login") {
                LoginScreen(viewModel = viewModel, navController = navController)
            }
            composable("email_login") {
                EmailLoginScreen(viewModel = viewModel, navController = navController)
            }
            composable("email_registration") {
                RegistrationScreen(viewModel = viewModel, navController = navController)
            }
            composable("forgot_password") {
                ForgotPasswordScreen(viewModel = viewModel, navController = navController)
            }

            // Main app screens
            composable("Homepage") {
                ScreenWithDrawer(
                    title = "Homepage",
                    drawerState = drawerState
                ) { paddingValues ->
                    HomepageScreen(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController
                    )
                }
            }
            composable("Calendar") {
                ScreenWithDrawer(
                    title = "Calendar",
                    drawerState = drawerState
                ) { paddingValues ->
                    CalendarScreen(modifier = Modifier.padding(paddingValues))
                }
            }
            composable("Stats") {
                ScreenWithDrawer(
                    title = "Stats",
                    drawerState = drawerState
                ) { paddingValues ->
                    StatsScreen(modifier = Modifier.padding(paddingValues))
                }
            }
            composable("Work") {
                ScreenWithDrawer(
                    title = "Work",
                    drawerState = drawerState
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "Work Screen Coming Soon",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
            composable("Friends") {
                ScreenWithDrawer(
                    title = "Friends",
                    drawerState = drawerState
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "Friends Screen Coming Soon",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
            composable("Freetime") {
                ScreenWithDrawer(
                    title = "Freetime",
                    drawerState = drawerState
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "Freetime Screen Coming Soon",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
            composable("Goalification") {
                ScreenWithDrawer(
                    title = "Goalification",
                    drawerState = drawerState
                ) { paddingValues ->
                    GoalificationScreen(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController,
                        viewModel = viewModel()
                    )
                }
            }
            composable(
                route = "SelectGoalsTasksScreen/{buttonIndex}",
                arguments = listOf(navArgument("buttonIndex") { type = NavType.IntType })
            ) { backStackEntry ->
                val buttonIndex = backStackEntry.arguments?.getInt("buttonIndex") ?: 0
                ScreenWithDrawer(
                    title = "Select Goals & Tasks",
                    drawerState = drawerState
                ) { paddingValues ->
                    SelectGoalsTasksScreen(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController,
                        buttonIndex = buttonIndex
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenWithDrawer(
    title: String,
    drawerState: DrawerState,
    content: @Composable (PaddingValues) -> Unit
) {
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu Icon")
                    }
                }
            )
        },
        content = content
    )
}

@Composable
fun DrawerContent(onMenuItemClick: (String) -> Unit, username: String, onLogoutClick: () -> Unit) {
    val isBoy = remember { mutableStateOf(true) }

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

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_small)))

        Icon(
            painter = painterResource(id = if (isBoy.value) R.drawable.ic_boy else R.drawable.ic_girl),
            contentDescription = "Avatar",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(80.dp)
                .padding(8.dp)
                .clickable {
                    isBoy.value = !isBoy.value
                }
        )

        Text(
            text = "Welcome back",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = username,
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_large)))

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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLogoutClick,
            modifier = Modifier.align(Alignment.Start),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary))
        ) {
            Text(text = "Logout", color = Color.Black)
        }
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
