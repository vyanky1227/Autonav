@file:JvmName("MainActivityKt")

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import android.widget.Toast.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.autonav.R
import com.example.autonav.ui.theme.AutonavTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale

class MainActivity3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutonavTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // Set up the navigation controller
                    val navController = rememberNavController()
                    // Set up the navigation graph
                    NavHost(navController = navController, startDestination = "screen3") {
                        composable("screen3") {
                            Screen3(navController = navController)
                        }
                        composable("screen4") {
                            Screen4() // Define Screen4 here
                        }
                        // Add other screens here if needed
                    }
                }
            }
        }
    }
}


@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier,
    colorShimmer: Color = Color.LightGray.copy(alpha = 0.6f),
    durationMillis: Int = 1000
) {
    val transition = rememberInfiniteTransition()
    val shimmerAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
    )
    val gradient = Brush.linearGradient(
        colors = listOf(
            colorShimmer.copy(alpha = 0.4f),
            colorShimmer,
            colorShimmer.copy(alpha = 0.4f)
        ),
        start = Offset.Zero,
        end = Offset(x = shimmerAnimation.value * 2000f, y = 0f)
    )

    Spacer(
        modifier = modifier
            .background(gradient)
    )
}

@Composable
fun Screen3(navController: NavController) {
    var text by remember { mutableStateOf("") }
    var addressText by remember { mutableStateOf("Fetching location...") }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).build()

    // Single state for selected favorite in Personalized Suggestions section
    var selectedFavoriteSuggestion by remember { mutableStateOf(-1) }

    // State for selected favorite in Categories section
    var selectedFavoriteCategory by remember { mutableStateOf(-1) }

    // Speech recognition launcher
    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val speechResult = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!speechResult.isNullOrEmpty()) {
                text = speechResult[0] // Update the text with the recognized speech
            }
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            requestLocationUpdates(fusedLocationClient, locationRequest, context) { address ->
                addressText = address ?: "Unable to fetch location"
                isLoading = false
            }
        } else {
            addressText = "Permission Denied"
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                requestLocationUpdates(fusedLocationClient, locationRequest, context) { address ->
                    addressText = address ?: "Unable to fetch location"
                    isLoading = false
                }
            }
            else -> {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            if (isLoading) {
                ShimmerEffect(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                ShimmerEffect(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                ShimmerEffect(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                // Display address and search bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = addressText,
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.user_icon),
                        contentDescription = "USER ICON",
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.Gray, CircleShape)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = text,
                    onValueChange = { newText -> text = newText },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(24.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                    trailingIcon = {
                        Row {
                            val customIcon: ImageVector = ImageVector.vectorResource(id = R.drawable.mic)
                            Icon(
                                imageVector = customIcon,
                                contentDescription = "Mic Icon",
                                modifier = Modifier.clickable {
                                    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                                        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                                        putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...")
                                    }
                                    try {
                                        speechLauncher.launch(intent)
                                    } catch (e: Exception) {
                                        makeText(context, "Speech recognition is not available", LENGTH_SHORT).show()
                                    }
                                }
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recommended Destinations",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    // Add-ons on the right side
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "See All",
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.LightGray
                            ),
                            modifier = Modifier
                                .clickable {
                                    // Navigate to Screen4
                                    navController.navigate("screen4")
                                }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More Options",
                            tint = Color.LightGray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(150.dp)
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.image3),
                                    contentDescription = "Image ",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(16.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                // Button aligned at the top start
                                Button(
                                    onClick = { /* Handle button click */ },
                                    modifier = Modifier
                                        .align(Alignment.TopStart) // Align the button to the top start of the Box
                                        .padding(5.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                                ) {
                                    Text("Explore", color = Color.White)
                                }
                            }

                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Favorite Icon",
                                tint = if (selectedFavoriteSuggestion == 0) Color.Red else Color.White,
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(5.dp)
                                    .clickable {
                                        selectedFavoriteSuggestion = if (selectedFavoriteSuggestion == 0) -1 else 0
                                    }
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(150.dp)
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.image4),
                                    contentDescription = "Image 4",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(16.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                // Button aligned at the top start
                                Button(
                                    onClick = { /* Handle button click */ },
                                    modifier = Modifier
                                        .align(Alignment.TopStart) // Align the button to the top start of the Box
                                        .padding(5.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                                ) {
                                    Text("Explore", color = Color.White)
                                }
                            }

                            // Handle favorites differently
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Favorite Icon",
                                tint = if (selectedFavoriteSuggestion == 1) Color.Red else Color.White,
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(5.dp)
                                    .clickable {
                                        selectedFavoriteSuggestion = if (selectedFavoriteSuggestion == 1) -1 else 1
                                    }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Categories",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(5) { index ->
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(Color.White, RoundedCornerShape(16.dp))
                                .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                                .padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Icon $index",
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.Center),
                                tint = if (selectedFavoriteCategory == index + 2) Color.Red else Color.White
                            )
                            // Handle favorite icon click in categories
                            Modifier.clickable {
                                selectedFavoriteCategory = if (selectedFavoriteCategory == index + 2) -1 else index + 2
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Personalized Suggestions",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(160.dp)
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.image1),
                            contentDescription = "Image 1",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Button(
                            onClick = { /* Handle button click */ },
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(5.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                        ) {
                            Text("Festival", color = Color.White)
                        }
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite Icon",
                            tint = if (selectedFavoriteSuggestion == 2) Color.Red else Color.White,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(5.dp)
                                .align(Alignment.TopEnd)
                                .clickable {
                                    selectedFavoriteSuggestion = if (selectedFavoriteSuggestion == 2) -1 else 2
                                }
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(160.dp)
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.image2),
                            contentDescription = "Image 2",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Button(
                            onClick = { /* Handle button click */ },
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(5.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                        ) {
                            Text("Movies", color = Color.White)
                        }
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite Icon",
                            tint = if (selectedFavoriteSuggestion == 3) Color.Red else Color.White,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(5.dp)
                                .align(Alignment.TopEnd)
                                .clickable {
                                    selectedFavoriteSuggestion = if (selectedFavoriteSuggestion == 3) -1 else 3
                                }
                        )
                    }
                }
            }
        }
    }
}





@SuppressLint("MissingPermission")

fun requestLocationUpdates(
    fusedLocationClient: FusedLocationProviderClient,
    locationRequest: LocationRequest,
    context: Context,
    onLocationFetched: (String?) -> Unit
) {
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val location = locationResult.lastLocation
            if (location != null) {
                // Use a Coroutine to handle the Geocoding asynchronously
                CoroutineScope(Dispatchers.IO).launch {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses = try {
                        geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    } catch (e: IOException) {
                        null
                    }

                    val address = addresses?.firstOrNull()
                    val city = address?.locality ?: ""
                    val state = address?.adminArea ?: ""
                    val country = address?.countryName ?: ""
                    val fullAddress = "$city, $state, $country"

                    // Update the UI on the main thread
                    withContext(Dispatchers.Main) {
                        onLocationFetched(fullAddress)
                    }
                }
            } else {
                onLocationFetched("Unable to fetch address")
            }
            fusedLocationClient.removeLocationUpdates(this) // Stop location updates
        }
    }

    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen3() {
    AutonavTheme {
        Screen3(navController = rememberNavController())
    }
}
