package com.devduo.kotlinflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DataProvider {
    private val _state:MutableStateFlow<String> = MutableStateFlow("")
    val state:StateFlow<String> = _state

    fun getData(){
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            _state.value = "Hello From StateFlow"
        }
    }
}