package com.example.autonav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.autonav.ui.theme.AutonavTheme

class MainActivity1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutonavTheme {
                // navcontroller set kiya usse screen 2 par ja sakte hai
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "screen1") {
                    composable("screen1") { Screen1(navController) }
                    composable("screen2") { Screen2(navController) }
                }
            }
        }
    }
}

@Composable
fun Screen1(navController: NavController) {
    // jab app open hoga tab app icon ko fade in karne ke liye
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 1000)
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 0.dp),
        color = MaterialTheme.colorScheme.background // isse background color set kiya
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "App Icon",
                modifier = Modifier
                    .size(250.dp)
                    .graphicsLayer(alpha = alpha)
            )
            Text(
                text = "Autonav",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Your Guide to Festivals and More",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 20.sp,
                    color = Color.Gray
                ),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(70.dp))
            Button(
                onClick = { navController.navigate("screen2") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF76D728)
                ),
                modifier = Modifier.width(250.dp)
            ) {
                Text(text = "Get Started")
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewScreen1() {
    AutonavTheme {
        val navController = rememberNavController()
        Screen1(navController)
    }
}

