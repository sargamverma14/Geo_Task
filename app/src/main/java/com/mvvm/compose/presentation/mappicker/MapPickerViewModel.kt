package com.mvvm.compose.presentation.mappicker

import com.mvvm.compose.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapPickerViewModel @Inject constructor() :
    BaseViewModel<MapPickerState, MapPickerEvent, MapPickerEffect>(MapPickerState()) {

    override fun onEvent(event: MapPickerEvent) {
        when (event) {
            is MapPickerEvent.UpdateSearchQuery -> setState { copy(searchQuery = event.query) }
            is MapPickerEvent.UpdateRadius -> setState { copy(radius = event.radius) }
            is MapPickerEvent.UpdateTrigger -> setState { copy(triggerType = event.trigger) }
            is MapPickerEvent.OnNextClicked -> sendEffect(MapPickerEffect.NavigateNext)
            is MapPickerEvent.OnBackClicked -> sendEffect(MapPickerEffect.NavigateBack)
            is MapPickerEvent.OnGpsClicked -> {
                sendEffect(MapPickerEffect.ShowSnackbar("Centered to current location"))
            }
        }
    }
}
