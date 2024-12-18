package com.dra.speakeaseappstaff.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dra.speakeaseappstaff.model.LocalizedStrings
import com.dra.speakeaseappstaff.viewmodel.ProfileViewModel
import com.google.firebase.database.FirebaseDatabase
import java.util.Locale

@Composable
fun EmergencyScreen(
    selectedLocale: MutableState<Locale>,
    onExit: () -> Unit
) {
    val emergencyText: List<String> = LocalizedStrings.getEmergencyText(selectedLocale.value)

    // ViewModel instance
    val profileViewModel: ProfileViewModel = viewModel()

    LaunchedEffect(Unit) {
        profileViewModel.setEmergencyStatus(true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.error)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
                .systemBarsPadding()
        ) {
            Text(
                text = emergencyText[0],
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onError,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = emergencyText[1],
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onError,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .align(Alignment.Center)
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Bell Icon",
                tint = MaterialTheme.colorScheme.onError,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .systemBarsPadding()
        ) {
            Text(
                text = emergencyText[2],
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onError
            )
        }
    }
}