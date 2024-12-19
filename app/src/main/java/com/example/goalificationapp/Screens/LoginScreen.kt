package com.example.goalificationapp.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goalificationapp.R
import com.example.goalificationapp.ui.theme.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
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
            text = "Register",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Sign in to take full advantage of Goalcut.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { /* TODO: Handle email registration */ },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primary)),
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            Text(text = "Register with email", fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = "Arrow", tint = Color.Black)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Or log in with:", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* TODO: Handle Google login */ }) {
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

            IconButton(onClick = { /* TODO: Handle Facebook login */ }) {
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
                }
            },
            style = TextStyle(fontSize = 16.sp, color = Color.Black)
        )
    }
}

