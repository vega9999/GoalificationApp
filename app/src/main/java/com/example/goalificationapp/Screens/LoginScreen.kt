package com.example.goalificationapp.Screens

import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.goalificationapp.R
import com.example.goalificationapp.ui.theme.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(viewModel: LoginViewModel, navController: NavController) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    if (isLoggedIn) {
        navController.navigate("main_screen")
        return
    }

    val annotatedText = buildAnnotatedString {
        append("Use Goalcut without registering: ")
        val startIndex = length
        append("This way")
        addStyle(
            style = SpanStyle(color = colorResource(R.color.primary)),
            start = startIndex,
            end = length
        )
        addStringAnnotation(
            tag = "THIS_WAY",
            annotation = "clickable",
            start = startIndex,
            end = length
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.bg_color))
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(120.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Welcome to Goalcut",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Sign in or register to take full advantage of Goalcut.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("email_login") },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primary)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "Login with email", fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = "Arrow", tint = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("email_registration") },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primary)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "Register with email", fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = "Arrow", tint = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*viewModel.loginWithGoogle()*/ }) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(colorResource(id = R.color.primary), CircleShape)
                        .padding(12.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google Login",
                        tint = Color.Black,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            IconButton(onClick = { /*viewModel.loginWithFacebook()*/ }) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(colorResource(id = R.color.primary), CircleShape)
                        .padding(12.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_facebook),
                        contentDescription = "Facebook Login",
                        tint = Color.Black,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        ClickableText(
            text = annotatedText,
            onClick = { offset ->
                annotatedText.getStringAnnotations(
                    tag = "THIS_WAY",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    viewModel.useWithoutRegistering()
                    navController.navigate("main_screen")
                }
            },
            style = TextStyle(fontSize = 16.sp, color = Color.Black)
        )
    }
}

@Composable
fun EmailLoginScreen(viewModel: LoginViewModel, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.loginWithEmail(email, password)
                navController.navigate("main_screen")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { navController.navigate("forgot_password") }
        ) {
            Text("Forgot password?")
        }
    }
}

