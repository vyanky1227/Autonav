package com.example.autonav

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun Screen4Festival(navController: NavController) {
    // Simulate loading state
    var isLoading by remember { mutableStateOf(true) }

    // Simulate a network delay for loading
    LaunchedEffect(Unit) {
        // Simulate loading time (e.g., fetching from API)
        delay(2000) // 2 seconds delay
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Upcoming Hindu Indian Festivals",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            // Show shimmering effect while loading
            ShimmerEffect()
        } else {
            // Map of festivals and their corresponding Wikipedia URLs
            val festivalUrls = mapOf(
                "03/10/24 - Sharad Navraatri, Ghatasthapanaa" to "https://en.wikipedia.org/wiki/Navaratri",
                "11/10/24 - Durga Maha Naavami Puja, Durga Puja Ashtaami" to "https://en.wikipedia.org/wiki/Durga_Puja",
                "12/10/24 - Dussehra, Sharaad Navraatri Paraana" to "https://en.wikipedia.org/wiki/Dussehra",
                "13/10/24 - Durga Visarjaan" to "https://en.wikipedia.org/wiki/Durga_Visarjan",
                "20/10/24 - Sankashti Chaaturthi, Kaarva Chaauth" to "https://en.wikipedia.org/wiki/Sankashti_Chaturthi",
                "28/10/24 - Rama Ekadaashi" to "https://en.wikipedia.org/wiki/Rama_Ekadashi",
                "29/10/24 - Dhanteras, Pradosh Vraat (K)" to "https://en.wikipedia.org/wiki/Dhanteras",
                "30/10/24 - Masik Shivaraatri" to "https://en.wikipedia.org/wiki/Masik_Shivaratri",
                "31/10/24 - Narak Chaturdaashi" to "https://en.wikipedia.org/wiki/Naraka_Chaturdashi",
                "01/11/24 - Diwali, Kaartik Amavasya" to "https://en.wikipedia.org/wiki/Diwali",
                "02/11/24 - Govardhan Pujaa" to "https://en.wikipedia.org/wiki/Govardhan_Puja",
                "03/11/24 - Bhaii Dooj" to "https://en.wikipedia.org/wiki/Bhai_Dooj",
                "07/11/24 - Chhath Puja" to "https://en.wikipedia.org/wiki/Chhath"
            )

            LazyColumn {
                items(festivalUrls.keys.toList()) { festival ->
                    FestivalItem(festival = festival) {
                        // Get the corresponding URL for the clicked festival
                        val url = festivalUrls[festival]
                        // Launch the browser intent
                        url?.let {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                            navController.context.startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShimmerEffect() {
    // Show shimmering effect for three dummy items
    Column(modifier = Modifier.fillMaxSize()) {
        repeat(3) { // Simulating three festival items
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .shimmerEffect()
                )
            }
        }
    }
}

// Shimmer Modifier Extension
fun Modifier.shimmerEffect(): Modifier {
    return this.then(Modifier.background(Color.Gray.copy(alpha = 0.5f))) // Apply a shimmer-like effect
}

@Composable
fun FestivalItem(festival: String, onExploreClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = festival,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onExploreClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF76D728)),
                    modifier = Modifier
                ) {
                    Text(text = "Explore")
                }
            }
        }
    }
}
