package com.dra.speakeaseappstaff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dra.speakeaseappstaff.viewmodel.ProfileViewModel
import kotlinx.coroutines.delay

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    onLogout: () -> Unit
) {
    val profile by viewModel.profile.collectAsState()

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.fetchProfile()
            delay(10_000L)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .padding(16.dp, 16.dp, 16.dp, 0.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Account",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(0.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = "Name",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "Gender",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "Age",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = profile.name,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 16.sp
                                )
                            )
                            Text(
                                text = profile.gender,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 16.sp
                                )
                            )
                            Text(
                                text = profile.age.toString(),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 16.sp
                                )
                            )
                        }
                    }
                }
            }
        }

        item {
            ReusableGrid(
                tableName = "Diagnoses",
                headers = listOf("Condition", "Diagnosed By"),
                items = profile.diagnoses.map { listOf(it.condition, it.diagnosedBy) }
            )
        }

        item {
            ReusableGrid(
                tableName = "Actions",
                headers = listOf("Action", "Performed By"),
                items = profile.actionsByStaff.map { listOf(it.action, it.performedBy) }
            )
        }

        item {
            ReusableGrid(
                tableName = "Medicine",
                headers = listOf("Medicine", "Dosage"),
                items = profile.medicinesTaken.map { listOf(it.medicine, "${it.dosage} (${it.frequency})") }
            )
        }

        item {
            ReusableGrid(
                tableName = "Checkups",
                headers = listOf("Checkup", "Performed By"),
                items = profile.checkups.map { listOf(it.checkup, it.performedBy) }
            )
        }

        item {
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text("Logout")
            }
        }
    }
}

@Composable
fun ReusableGrid(
    tableName: String,
    headers: List<String>,
    items: List<List<String>>,
    modifier: Modifier = Modifier
) {
    val totalItems = headers.size + items.size * headers.size

    Box (
        modifier = Modifier
    ){
        Box(
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 0.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp)
                )
                .padding(12.dp, 4.dp, 12.dp, 16.dp)
        ) {
            Text(
                text = tableName,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    fontSize = 14.sp
                )
            )
        }

        Box(
            modifier = Modifier
                .padding(16.dp, 40.dp, 16.dp, 16.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(16.dp, 16.dp, 16.dp, 16.dp)
                )
                .padding(16.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(headers.size),
                modifier = modifier
                    .fillMaxWidth()
                    .height(calculateDynamicHeight(headers.size, totalItems)),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(totalItems) { index ->
                    val row = index / headers.size
                    val column = index % headers.size
                    val isChecker = (row + column) % 2 == 0

                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(
                                if (isChecker) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.tertiary
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        BasicText(
                            text = when (row) {
                                0 -> headers[column]
                                else -> {
                                    val itemIndex = row - 1
                                    if (itemIndex < items.size) {
                                        items[itemIndex][column]
                                    } else {
                                        "" // Fallback for empty cells if needed
                                    }
                                }
                            },
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }
}

fun calculateDynamicHeight(columns: Int, totalItems: Int): Dp {
    val rows = (totalItems + columns - 1) / columns
    val rowHeight = 64.dp + 4.dp
    return rows * rowHeight
}