package com.mvvm.compose.presentation.mappicker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MapPickerScreen(
    onNavigateBack: () -> Unit,
    onNavigateNext: () -> Unit,
    viewModel: MapPickerViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is MapPickerEffect.NavigateBack -> onNavigateBack()
                is MapPickerEffect.NavigateNext -> onNavigateNext()
                is MapPickerEffect.ShowSnackbar -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color(0xFFEAF5F2)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Map Mock Background (Grid of blocks and streets)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFEAF5F2)),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(4) { rowIndex ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        repeat(3) { colIndex ->
                            Box(
                                modifier = Modifier
                                    .size(110.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(if ((rowIndex + colIndex) % 2 == 0) Color(0xFFDFEFEA) else Color(0xFFF4F8F6))
                            )
                        }
                    }
                }
            }

            // Centered Marker Pin with Radar Circle
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF006A4E).copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF006A4E).copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF006A4E)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location Pin",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }

            // Top Bar: Back Button & Search Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 4.dp,
                    modifier = Modifier.size(48.dp)
                ) {
                    IconButton(onClick = { viewModel.onEvent(MapPickerEvent.OnBackClicked) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF1A1C1A)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(28.dp),
                    color = Color.White,
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Gray
                        )
                        OutlinedTextField(
                            value = state.searchQuery,
                            onValueChange = { viewModel.onEvent(MapPickerEvent.UpdateSearchQuery(it)) },
                            placeholder = { Text("Search place", color = Color.Gray) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                disabledBorderColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            )
                        )
                    }
                }
            }

            // Floating GPS Crosshair Button (Bottom Right of map area)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 340.dp, end = 24.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .size(52.dp)
                        .clickable { viewModel.onEvent(MapPickerEvent.OnGpsClicked) }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = "GPS",
                            tint = Color(0xFF006A4E),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // Bottom Sheet Card
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Surface(
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                    color = Color.White,
                    shadowElevation = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        // Handle bar
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Color(0xFFD0D5DD))
                            )
                        }

                        // Location Header
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFC2E7E0)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "Location",
                                    tint = Color(0xFF005C42),
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = state.locationName,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1A1C1A)
                                )
                                Text(
                                    text = state.locationAddress,
                                    fontSize = 13.sp,
                                    color = Color.Gray
                                )
                            }
                        }

                        // Reminder Radius Section
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Reminder radius",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF414943)
                                )
                                Text(
                                    text = "${state.radius.toInt()} m",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF006A4E)
                                )
                            }

                            Slider(
                                value = state.radius,
                                onValueChange = { viewModel.onEvent(MapPickerEvent.UpdateRadius(it)) },
                                valueRange = 100f..1000f,
                                colors = SliderDefaults.colors(
                                    thumbColor = Color(0xFF006A4E),
                                    activeTrackColor = Color(0xFF006A4E),
                                    inactiveTrackColor = Color(0xFFE2F3EF)
                                )
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "100 m", fontSize = 12.sp, color = Color.Gray)
                                Text(text = "1 km", fontSize = 12.sp, color = Color.Gray)
                            }
                        }

                        // Trigger Buttons ("When I arrive" vs "When I leave")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            val isArrive = state.triggerType == "When I arrive"
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = if (isArrive) Color(0xFFC2E7E0) else Color(0xFFF1F5F2),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { viewModel.onEvent(MapPickerEvent.UpdateTrigger("When I arrive")) }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 14.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "→ When I arrive",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isArrive) Color(0xFF00382B) else Color(0xFF414943)
                                    )
                                }
                            }

                            val isLeave = state.triggerType == "When I leave"
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = if (isLeave) Color(0xFFC2E7E0) else Color(0xFFF1F5F2),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { viewModel.onEvent(MapPickerEvent.UpdateTrigger("When I leave")) }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 14.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "← When I leave",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isLeave) Color(0xFF00382B) else Color(0xFF414943)
                                    )
                                }
                            }
                        }

                        // Next Button
                        Button(
                            onClick = { viewModel.onEvent(MapPickerEvent.OnNextClicked) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9531E))
                        ) {
                            Text(
                                text = "Next",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
