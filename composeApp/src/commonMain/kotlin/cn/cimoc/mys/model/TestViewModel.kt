package cn.cimoc.mys.model

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {
    private var _number = mutableStateOf(0)
    val number: State<Int> = _number

    fun add() {
        _number.value += 1
    }
}