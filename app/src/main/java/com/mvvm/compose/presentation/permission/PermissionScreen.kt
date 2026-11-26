package com.mvvm.compose.presentation.permission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.mvvm.compose.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mvvm.compose.ui.theme.NewComposeWithMVVMTheme
import kotlinx.coroutines.flow.collectLatest

data class PermissionStepInfo(
    val title: String,
    val subtitle: String,
    val badgeIcon: ImageVector,
    val imageRes: Int
)

@Composable
fun PermissionScreen(
    onNavigateNext: () -> Unit,
    viewModel: PermissionViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is PermissionEffect.NavigateNext -> onNavigateNext()
                is PermissionEffect.ShowSnackbar -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    val stepInfo = when (state.currentStep) {
        1 -> PermissionStepInfo(
            "Location access",
            "We need your location to know when you reach a place",
            Icons.Default.LocationOn,
            R.drawable.permission
        )
        2 -> PermissionStepInfo(
            "Allow all the time",
            "So reminders work even when the app is closed",
            Icons.Default.LocationOn,
            R.drawable.permision2
        )
        else -> PermissionStepInfo(
            "Notifications",
            "So we can alert you at the right place",
            Icons.Default.Notifications,
            R.drawable.permission3
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color(0xFFF8F9F8)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar: Step indicator & Dark mode toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Step indicator pill bar
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 1..3) {
                        val isActive = state.currentStep == i
                        Box(
                            modifier = Modifier
                                .width(if (isActive) 20.dp else 6.dp)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(if (isActive) Color(0xFF006A4E) else Color(0xFFD0D5DD))
                        )
                    }
                }

                Surface(
                    shape = CircleShape,
                    color = Color(0xFFE8F5E9),
                    modifier = Modifier.size(40.dp)
                ) {
                    IconButton(onClick = { viewModel.onEvent(PermissionEvent.ToggleDarkMode) }) {
                        Icon(
                            painterResource(R.drawable.outline_nightlight_24),
                            contentDescription = "Settings / Mode",
                            tint = Color(0xFF1A1C1A),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Center Card matching the screenshot
            Surface(
                shape = RoundedCornerShape(28.dp),
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.82f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Permission Illustration Image
                    Image(
                        painter = painterResource(id = stepInfo.imageRes),
                        contentDescription = "Permission Illustration",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Icon Badge
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFC2E7E0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = stepInfo.badgeIcon,
                            contentDescription = "Permission Icon",
                            tint = Color(0xFF005C42),
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Title
                    Text(
                        text = stepInfo.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1C1A),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Subtitle
                    Text(
                        text = stepInfo.subtitle,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Buttons
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { viewModel.onEvent(PermissionEvent.AllowPermission) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9531E))
                        ) {
                            Text(
                                text = "Allow",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Text(
                            text = "Skip",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF414943),
                            modifier = Modifier
                                .clickable { viewModel.onEvent(PermissionEvent.SkipPermission) }
                                .padding(8.dp)
                        )
                    }
                }
            }

            // Bottom Step Indicator Text
            Text(
                text = "${state.currentStep} of ${state.totalSteps}",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1A1C1A),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PermissionScreenPreview() {
    NewComposeWithMVVMTheme {
        PermissionScreen(onNavigateNext = {})
    }
}
