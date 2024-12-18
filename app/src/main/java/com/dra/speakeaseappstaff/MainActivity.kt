package com.dra.speakeaseappstaff

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dra.speakeaseappstaff.navigation.NavRoute
import com.dra.speakeaseappstaff.ui.screens.EmergencyScreen
import com.dra.speakeaseappstaff.ui.screens.LoginScreen
import com.dra.speakeaseappstaff.ui.screens.MainScreen
import com.dra.speakeaseappstaff.viewmodel.ProfileViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale

class MainActivity : ComponentActivity() {
    private var backPressedOnce = false
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val auth = FirebaseAuth.getInstance()
            val isLoggedIn = auth.currentUser != null

            val selectedLocale = remember { mutableStateOf(Locale.ENGLISH) }

            // ViewModel instance
            val profileViewModel: ProfileViewModel = viewModel()
            val isEmergency = profileViewModel.isEmergency.collectAsState()

            // Observe emergency status and navigate accordingly
            LaunchedEffect(isEmergency.value) {
                if (isEmergency.value) {
                    navController.navigate(NavRoute.Emergency.route)
                } else {
                    if (navController.currentDestination?.route == NavRoute.Emergency.route) {
                        navController.navigate("main") {
                            popUpTo("main") { inclusive = true }
                        }
                    }
                }
            }

            NavHost(
                navController = navController,
                startDestination = if (isLoggedIn) "main" else "login"
            ) {
                composable("login") {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate("main") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    )
                }

                composable("main") {
                    MainScreen(
                        selectedLocale = selectedLocale,
                        onLogout = {
                            auth.signOut()
                            navController.navigate("login") {
                                popUpTo("main") { inclusive = true }
                            }
                        }
                    )
                }

                composable(NavRoute.Emergency.route) {
                    EmergencyScreen(
                        selectedLocale = selectedLocale,
                        onExit = {
                            navController.navigate("main") {
                                popUpTo("main") { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }

    @Deprecated("This method has been deprecated")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (backPressedOnce) {
            finishAffinity()
            return
        }

        backPressedOnce = true
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()

        handler.postDelayed({ backPressedOnce = false }, 2000)
    }
}