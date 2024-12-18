package com.dra.speakeaseappstaff.ui.screens

import android.Manifest
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dra.speakeaseappstaff.navigation.NavRoute
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.dra.speakeaseappstaff.ui.components.BottomNavBar
import com.dra.speakeaseappstaff.ui.components.TopBar
import java.util.Locale

@Composable
fun MainScreen(
    selectedLocale: MutableState<Locale>,
    onLogout: () -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavBar(navController, selectedLocale = selectedLocale) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavRoute.History.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavRoute.History.route) {
                HistoryScreen()
            }
            composable(NavRoute.Scan.route) {
                PermissionRequired(
                    permission = android.Manifest.permission.CAMERA,
                    rationale = "Camera access is required for scanning."
                ) {
                    ScanScreen()
                }
            }

            composable(NavRoute.Profile.route) {
                ProfileScreen(onLogout = onLogout)
            }
        }
    }
}