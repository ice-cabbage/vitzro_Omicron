package com.stonetranslator.omicron

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Timer

class InputValueViewModel: ViewModel() {
    private val _svState = MutableStateFlow(InputValueState())
    val svState = _svState.asStateFlow()

    fun onEvent(event: InputValueEvent) {
        when(event) {
            is InputValueEvent.RunningChanged-> _svState.value = _svState.value.copy(running = event.value)
            is InputValueEvent.Apply-> _svState.value = event.value
        }
    }

    var svOffset = 0
    var timer: Timer? = null
}