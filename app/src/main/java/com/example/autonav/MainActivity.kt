package com.example.autonav

import Screen3
import Screen4
import android.content.IntentFilter
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.autonav.ui.theme.AutonavTheme

class MainActivity : ComponentActivity() {

    // Declare the receiver as a property of the activity
    private val incomingCallReceiver = IncomingCallReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutonavTheme {
                // Initialize NavController
                val navController = rememberNavController()



                // Setup Navigation Graph
                NavHost(navController = navController, startDestination = "screen1") {
                    composable("screen1") { Screen1(navController) }
                    composable("screen2") { Screen2(navController) }
                    composable("screen3") { Screen3(navController) }
                    composable("screen4") { Screen4() }
                    composable("screen4_festival") { Screen4Festival(navController) }

                }


            }

            // Register the IncomingCallReceiver
            val filter = IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
            registerReceiver(incomingCallReceiver, filter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the receiver to prevent memory leaks
        unregisterReceiver(incomingCallReceiver)
    }
}
