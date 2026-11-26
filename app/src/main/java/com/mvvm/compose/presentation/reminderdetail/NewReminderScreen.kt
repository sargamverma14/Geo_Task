package com.mvvm.compose.presentation.reminderdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mvvm.compose.ui.theme.NewComposeWithMVVMTheme
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewReminderScreen(
    onNavigateBack: () -> Unit,
    onNavigateToMapPicker: () -> Unit,
    viewModel: NewReminderViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is NewReminderEffect.NavigateBack -> onNavigateBack()
                is NewReminderEffect.ShowSnackbar -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    if (showStartTimePicker) {
        TimePickerDialog(
            title = "Select Start Time",
            initialTime = state.startTime,
            onDismiss = { showStartTimePicker = false },
            onConfirm = { time ->
                viewModel.onEvent(NewReminderEvent.UpdateStartTime(time))
                showStartTimePicker = false
            }
        )
    }

    if (showEndTimePicker) {
        TimePickerDialog(
            title = "Select End Time",
            initialTime = state.endTime,
            onDismiss = { showEndTimePicker = false },
            onConfirm = { time ->
                viewModel.onEvent(NewReminderEvent.UpdateEndTime(time))
                showEndTimePicker = false
            }
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
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Top Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconButton(onClick = { viewModel.onEvent(NewReminderEvent.NavigateBack) }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color(0xFF1A1C1A)
                            )
                        }
                        Text(
                            text = "New Reminder",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1C1A)
                        )
                    }

                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFF1F5F2),
                        modifier = Modifier.size(40.dp)
                    ) {
                        IconButton(onClick = { viewModel.onEvent(NewReminderEvent.ToggleDarkMode) }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings / Mode",
                                tint = Color(0xFF414943),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                // Card 1: Title and Note
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Title",
                            fontSize = 13.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                        OutlinedTextField(
                            value = state.title,
                            onValueChange = { viewModel.onEvent(NewReminderEvent.UpdateTitle(it)) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            placeholder = { Text("e.g. Buy milk", color = Color.Gray) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                disabledBorderColor = Color.Transparent,
                                focusedContainerColor = Color(0xFFF1F5F2),
                                unfocusedContainerColor = Color(0xFFF1F5F2)
                            )
                        )

                        Text(
                            text = "Note",
                            fontSize = 13.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                        OutlinedTextField(
                            value = state.note,
                            onValueChange = { viewModel.onEvent(NewReminderEvent.UpdateNote(it)) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            placeholder = { Text("Add details...", color = Color.Gray) },
                            minLines = 2,
                            maxLines = 5,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                disabledBorderColor = Color.Transparent,
                                focusedContainerColor = Color(0xFFF1F5F2),
                                unfocusedContainerColor = Color(0xFFF1F5F2)
                            )
                        )
                    }
                }

                // Card 2: Location Card
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Map mockup header
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFFF4F7F5)),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFC2E7E0)),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF006A4E)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "Pin",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = state.locationName,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1A1C1A)
                                )
                                Text(
                                    text = state.radius,
                                    fontSize = 13.sp,
                                    color = Color.Gray
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = Color(0xFFC2E7E0),
                                modifier = Modifier.clickable { onNavigateToMapPicker() }
                            ) {
                                Text(
                                    text = "Edit location",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF00382B),
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }

                // Card 3: Trigger & Time
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "Trigger",
                            fontSize = 13.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )

                        // Trigger segmented buttons
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFFF1F5F2))
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            val isArrive = state.triggerType == "Arrive"
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isArrive) Color(0xFF006A4E) else Color.Transparent)
                                    .clickable { viewModel.onEvent(NewReminderEvent.UpdateTrigger("Arrive")) }
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "→ Arrive",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isArrive) Color.White else Color(0xFF414943)
                                )
                            }

                            val isLeave = state.triggerType == "Leave"
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isLeave) Color(0xFF006A4E) else Color.Transparent)
                                    .clickable { viewModel.onEvent(NewReminderEvent.UpdateTrigger("Leave")) }
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "← Leave",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isLeave) Color.White else Color(0xFF414943)
                                )
                            }
                        }

                        // Repeat Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Repeat",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1A1C1A)
                                )
                                Text(
                                    text = "Every time",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                            Switch(
                                checked = state.repeat,
                                onCheckedChange = { viewModel.onEvent(NewReminderEvent.UpdateRepeat(it)) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color(0xFF006A4E),
                                    uncheckedThumbColor = Color.White,
                                    uncheckedTrackColor = Color(0xFFD0D5DD)
                                )
                            )
                        }

                        // Active time window
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Active time window",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A1C1A)
                            )
                            Text(
                                text = "Optional",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                TimeInputField(
                                    label = "Start time",
                                    time = state.startTime,
                                    onTimeChange = { viewModel.onEvent(NewReminderEvent.UpdateStartTime(it)) },
                                    onClockClick = { showStartTimePicker = true }
                                )
                            }

                            Box(modifier = Modifier.weight(1f)) {
                                TimeInputField(
                                    label = "End time",
                                    time = state.endTime,
                                    onTimeChange = { viewModel.onEvent(NewReminderEvent.UpdateEndTime(it)) },
                                    onClockClick = { showEndTimePicker = true }
                                )
                            }
                        }
                    }
                }
            }

            // Bottom Actions
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { viewModel.onEvent(NewReminderEvent.SaveReminder) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9531E))
                ) {
                    Text(
                        text = "Save Reminder",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Text(
                    text = "Delete",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFBA1A1A),
                    modifier = Modifier
                        .clickable { viewModel.onEvent(NewReminderEvent.DeleteReminder) }
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun TimeInputField(
    label: String,
    time: String,
    onTimeChange: (String) -> Unit,
    onClockClick: () -> Unit
) {
    val parts = time.split(":")
    var hour by remember(time) { mutableStateOf(parts.getOrNull(0) ?: "--") }
    var minute by remember(time) { mutableStateOf(parts.getOrNull(1) ?: "00") }

    var isHourFocused by remember { mutableStateOf(false) }
    var isMinuteFocused by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFF1F5F2),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    // Hour field
                    BasicTextField(
                        value = hour,
                        onValueChange = { newVal: String ->
                            val filtered = newVal.filter { it.isDigit() }.take(2).toString()
                            hour = filtered.ifEmpty { "--" }
                            onTimeChange("$hour:$minute")
                        },
                        modifier = Modifier
                            .background(
                                color = if (isHourFocused) Color(0xFF0079D3) else Color.Transparent,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                            .onFocusChanged { isHourFocused = it.isFocused },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isHourFocused) Color.White else Color(0xFF1A1C1A)
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Text(
                        text = ":",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1C1A)
                    )

                    // Minute field
                    BasicTextField(
                        value = minute,
                        onValueChange = { newVal: String ->
                            val filtered = newVal.filter { it.isDigit() }.take(2).toString()
                            minute = filtered.ifEmpty { "00" }
                            onTimeChange("$hour:$minute")
                        },
                        modifier = Modifier
                            .background(
                                color = if (isMinuteFocused) Color(0xFF0079D3) else Color.Transparent,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                            .onFocusChanged { isMinuteFocused = it.isFocused },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isMinuteFocused) Color.White else Color(0xFF1A1C1A)
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                IconButton(onClick = onClockClick, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Select Time",
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    title: String,
    initialTime: String = "08:00",
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val parts = initialTime.split(":")
    val initHour = parts.getOrNull(0)?.toIntOrNull() ?: 8
    val initMinute = parts.getOrNull(1)?.toIntOrNull() ?: 0

    val timePickerState = rememberTimePickerState(
        initialHour = initHour,
        initialMinute = initMinute,
        is24Hour = true
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val formatted = String.format(Locale.getDefault(), "%02d:%02d", timePickerState.hour, timePickerState.minute)
                    onConfirm(formatted)
                }
            ) {
                Text("OK", color = Color(0xFF006A4E), fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color.Gray)
            }
        },
        text = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TimePicker(state = timePickerState)
            }
        },
        title = {
            Text(text = title, fontWeight = FontWeight.Bold)
        },
        shape = RoundedCornerShape(24.dp),
        containerColor = Color.White
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun NewReminderScreenPreview() {
    NewComposeWithMVVMTheme {
        NewReminderScreen(onNavigateBack = {}, onNavigateToMapPicker = {})
    }
}
