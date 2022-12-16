package com.github.filipelipan.moviesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import javax.inject.Inject

data class TasksUiState(
    val isLoading: Boolean = false,
    val userMessage: String? = null
)

@HiltViewModel
class MainActivityViewModel @Inject constructor(
) : ViewModel() {

    val showToast = MutableStateFlow<String?>(null)
    val showToast2 = MutableStateFlow<String?>(null)
    private val _isLoading = MutableStateFlow(false)
    val uiState: StateFlow<TasksUiState> = combine(showToast, showToast2, _isLoading) { showToast, showToast2, _isLoading ->
        TasksUiState(
            isLoading = _isLoading,
            userMessage = showToast
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TasksUiState(
            false,
            null
        )
    )
}
