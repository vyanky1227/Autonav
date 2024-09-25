import android.os.Bundle
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
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White, // Color of the search icon
                        modifier = Modifier
                            .size(40.dp) // Size of the circle
                            .background(color = Color(0xFF76D728), shape = CircleShape)
                            .padding(8.dp)
                    )
                },
                placeholder = { Text("Search Destinations...") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Popular Searches text
            Text(
                text = "Popular Searches",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp)) // Add space between the buttons

            // Row for "Shopping Mall" and "City Center" buttons (two buttons per row)
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp), // Spacing between rows
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { /* Handle Shopping Mall action */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF76D728) // Change to desired color
                        ),
                        modifier = Modifier
                            .weight(1f) // Equal width
                    ) {
                        Text(text = "Shopping Mall")
                    }

                    Button(
                        onClick = { /* Handle City Center action */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF76D728) // Change to desired color
                        ),
                        modifier = Modifier
                            .weight(1f) // Equal width
                    ) {
                        Text(text = "City Center")
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { /* Handle Park action */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF76D728) // Change to desired color
                        ),
                        modifier = Modifier
                            .weight(1f) // Equal width
                    ) {
                        Text(text = "Park")
                    }

                    Button(
                        onClick = { /* Handle Airport action */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF76D728) // Change to desired color
                        ),
                        modifier = Modifier
                            .weight(1f) // Equal width
                    ) {
                        Text(text = "Airport")
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { /* Handle Restaurant action */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF76D728) // Change to desired color
                        ),
                        modifier = Modifier
                            .weight(1f) // Equal width
                    ) {
                        Text(text = "Restaurant")
                    }

                    Button(
                        onClick = { /* Handle Petrol Station action */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF76D728) // Change to desired color
                        ),
                        modifier = Modifier
                            .weight(1f) // Equal width
                    ) {
                        Text(text = "Petrol Station")
                    }
                }
            }
            // Row for "Shopping Mall" and "City Center" buttons
            // (Same code for buttons as before)

            Spacer(modifier = Modifier.height(16.dp))

            // Categories section
            Text(
                text = "Categories",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Checkbox for Food and Dining
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

            // Checkbox for Entertainment
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

            // Checkbox for Services
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

            // Checkbox for Landmark
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
                    .fillMaxWidth() // Box takes the full width
                    .height(200.dp) // Set the height to a smaller value
                    .padding(16.dp) // Optional padding around the Box
            ) {
                Button(
                    onClick = { /* Handle Petrol Station action */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF76D728) // Button color
                    ),
                    modifier = Modifier
                        .align(Alignment.Center) // Center the button in the Box
                        .size(width = 200.dp, height = 60.dp) // Adjust button size
                ) {
                    Text(
                        text = "Show Results",
                        fontSize = 20.sp // Adjust the text size as needed

                    )
                }
            }




            // Add more UI elements here if needed
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewScreen4() {
    AutonavTheme {
        Screen4()
    }
}




