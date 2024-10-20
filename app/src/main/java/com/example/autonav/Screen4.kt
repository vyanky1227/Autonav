import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autonav.ui.theme.AutonavTheme

class MainActivity4 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutonavTheme {
                Screen4()
            }
        }
    }
}

@Composable
fun Screen4() {
    var text by remember { mutableStateOf("") }

    // Checkbox states
    var isFoodAndDiningChecked by remember { mutableStateOf(false) }
    var isEntertainmentChecked by remember { mutableStateOf(false) }
    var isServicesChecked by remember { mutableStateOf(false) }
    var isLandmarkChecked by remember { mutableStateOf(false) }

    // Recent searches list
    var recentSearches by remember { mutableStateOf(mutableListOf<String>()) }

    val context = LocalContext.current // Get the current context

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 0.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        ) {
            // Search bar at the top
            OutlinedTextField(
                value = text,
                onValueChange = { newText -> text = newText },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(24.dp),
                leadingIcon = null, // No leading icon
                trailingIcon = {
                    IconButton(onClick = {
                        // Launch Google Maps with the entered text when the search icon is clicked
                        if (text.isNotEmpty()) {
                            // Add to recent searches
                            if (!recentSearches.contains(text)) {
                                recentSearches.add(0, text) // Add to the top
                            }
                            // Limit the size of recent searches to 5
                            if (recentSearches.size > 5) {
                                recentSearches.removeAt(recentSearches.size - 1) // Remove oldest
                            }

                            // Log recent searches for debugging
                            Log.d("RecentSearches", "Updated recent searches: $recentSearches")

                            val uri = Uri.parse("geo:0,0?q=$text")
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(intent)

                            // Clear the search text after submission
                            text = ""
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = Color.White, // Color of the search icon
                            modifier = Modifier
                                .size(40.dp) // Size of the circle
                                .background(color = Color(0xFF76D728), shape = CircleShape)
                                .padding(8.dp)
                        )
                    }
                },
                placeholder = { Text("Search Destinations...") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Popular Searches text
            Text(
                text = "Recent Searches",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp)) // Add space between the buttons

            // Display recent searches as buttons
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp), // Spacing between recent searches
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                // Check if recent searches are not empty
                if (recentSearches.isEmpty()) {
                    Text("No recent searches yet.", style = MaterialTheme.typography.bodySmall)
                } else {
                    for (search in recentSearches) {
                        Button(
                            onClick = {
                                text = search // Update search text
                                // Launch Google Maps with the recent search
                                val uri = Uri.parse("geo:0,0?q=$search")
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                context.startActivity(intent)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = search)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Categories section
            Text(
                text = "Categories",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Checkbox for categories
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Checkbox(
                    checked = isFoodAndDiningChecked,
                    onCheckedChange = { isFoodAndDiningChecked = it }
                )
                Text(text = "Food and Dining")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Checkbox(
                    checked = isEntertainmentChecked,
                    onCheckedChange = { isEntertainmentChecked = it }
                )
                Text(text = "Entertainment")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Checkbox(
                    checked = isServicesChecked,
                    onCheckedChange = { isServicesChecked = it }
                )
                Text(text = "Services")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Checkbox(
                    checked = isLandmarkChecked,
                    onCheckedChange = { isLandmarkChecked = it }
                )
                Text(text = "Landmark")
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        val selectedCategories = mutableListOf<String>()
                        if (isFoodAndDiningChecked) selectedCategories.add("Food and Dining")
                        if (isEntertainmentChecked) selectedCategories.add("Entertainment")
                        if (isServicesChecked) selectedCategories.add("Services")
                        if (isLandmarkChecked) selectedCategories.add("Landmark")

                        // Show results based on selected categories
                        showResults(context, selectedCategories) // Pass context to showResults
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF76D728) // Button color
                    ),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(width = 200.dp, height = 60.dp)
                ) {
                    Text(
                        text = "Show Results",
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

fun showResults(context: Context, selectedCategories: List<String>) {
    // Combine the selected categories into a query string
    val query = selectedCategories.joinToString(", ")
    val uri = Uri.parse("geo:0,0?q=$query")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(intent)
}



@Composable
@Preview(showBackground = true)
fun PreviewScreen4() {
    AutonavTheme {
        Screen4()
    }
}




