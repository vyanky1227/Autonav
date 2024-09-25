package com.example.autonav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.autonav.ui.theme.AutonavTheme
import kotlinx.coroutines.delay

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutonavTheme {
                val navController = rememberNavController()
                Screen2(navController)
            }
        }
    }
}

@Composable
fun Screen2(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    if (isLoading) {
        LaunchedEffect(Unit) {
            delay(2000L)
            if (email == "vyanky15dh@gmail.com" && password == "123") {
                navController.navigate("screen3")
            } else {
                errorMessage = "Incorrect email or password."
            }
            isLoading = false
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.screen2_icon),
                contentDescription = "App Icon",
                modifier = Modifier
                    .size(250.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email field
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email Icon",
                    modifier = Modifier.size(24.dp)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .weight(1f)
                        .width(200.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Password field
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Password Icon",
                    modifier = Modifier.size(24.dp)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .weight(1f)
                        .width(200.dp)
                )
            }


            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = errorMessage, color = Color.Red, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))


            if (isLoading) {
                CircularProgressIndicator(
                    color = Color(0xFF76D728),
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                Button(
                    onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            isLoading = true
                        } else {
                            errorMessage = "Please fill in both fields."
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF76D728)
                    ),
                    modifier = Modifier
                        .width(150.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Sign In")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {  },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3b5998)
                    ),
                    modifier = Modifier.width(150.dp)
                ) {
                    Text(text = "Facebook", color = Color.White)
                }
                Button(
                    onClick = {  },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFdb4437)
                    ),
                    modifier = Modifier.width(150.dp)
                ) {
                    Text(text = "Google", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen2() {
    AutonavTheme {
        val navController = rememberNavController()
        Screen2(navController)
    }
}
