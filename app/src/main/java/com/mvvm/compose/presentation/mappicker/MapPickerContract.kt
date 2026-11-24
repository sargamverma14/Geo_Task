package com.mvvm.compose.presentation.mappicker

data class MapPickerState(
    val searchQuery: String = "",
    val locationName: String = "City Market",
    val locationAddress: String = "420 Elm Street, Oakridge",
    val radius: Float = 300f, // in meters
    val triggerType: String = "When I arrive", // "When I arrive" or "When I leave"
    val isLoading: Boolean = false
)

sealed class MapPickerEvent {
    data class UpdateSearchQuery(val query: String) : MapPickerEvent()
    data class UpdateRadius(val radius: Float) : MapPickerEvent()
    data class UpdateTrigger(val trigger: String) : MapPickerEvent()
    object OnNextClicked : MapPickerEvent()
    object OnBackClicked : MapPickerEvent()
    object OnGpsClicked : MapPickerEvent()
}

sealed class MapPickerEffect {
    object NavigateBack : MapPickerEffect()
    object NavigateNext : MapPickerEffect()
    data class ShowSnackbar(val message: String) : MapPickerEffect()
}
