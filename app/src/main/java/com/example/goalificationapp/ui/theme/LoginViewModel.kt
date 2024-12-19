package com.example.goalificationapp.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> get() = _username

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    fun loginWithUsername(username: String) {
        viewModelScope.launch {
            _username.value = username
            _isLoggedIn.value = true
        }
    }

    fun useWithoutRegistering() {
        loginWithUsername("Guest")
    }

    fun logout() {
        viewModelScope.launch {
            _username.value = null
            _isLoggedIn.value = false
        }
    }
}

