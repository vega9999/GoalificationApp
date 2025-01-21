package com.example.goalificationapp.ui.theme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
/*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
 */
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.tasks.await

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> get() = _username

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val sharedPreferences = application.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

    init {
        val savedUsername = sharedPreferences.getString("username", null)
        if (savedUsername != null) {
            _username.value = savedUsername
            _isLoggedIn.value = true
        }
    }

    fun updateUsername(newUsername: String) {
        _username.value = newUsername
        sharedPreferences.edit().putString("username", newUsername).apply()
    }

    fun loginWithEmail(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                val user = result.user
                if (user != null) {
                    val username = user.displayName ?: "Unknown User"
                    _username.value = username
                    _isLoggedIn.value = true
                    saveLoginState(username)
                }
            } catch (e: Exception) {
                println("Login fehlgeschlagen: ${e.message}")
            }
        }
    }

    fun registerWithEmail(email: String, username: String, password: String) {
        viewModelScope.launch {
            try {
                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                val user = result.user
                if (user != null) {
                    val profileUpdates = userProfileChangeRequest {
                        displayName = username
                    }
                    user.updateProfile(profileUpdates).await()
                    _username.value = username
                    _isLoggedIn.value = true
                }
            } catch (e: Exception) {
                println("Registrierung fehlgeschlagen: ${e.message}")
            }
        }
    }

    fun useWithoutRegistering() {
        loginWithUsername("Guest")
    }

    private fun loginWithUsername(username: String) {
        viewModelScope.launch {
            _username.value = username
            _isLoggedIn.value = true
            saveLoginState(username)
        }
    }

    /*
    fun logout() {
        viewModelScope.launch {
            _username.value = null
            _isLoggedIn.value = false
            clearLoginState()
        }
    }
     */

    fun sendPasswordResetEmail(email: String) {
        // TODO: Implement actual password reset logic
        println("Password reset email sent to $email")
    }
    /*
    fun loginWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(getApplication(), gso)
        val signInIntent = googleSignInClient.signInIntent
        // Start the Google Sign-In activity
        // You would typically start this activity from your LoginScreen
        // and handle the result in your activity's onActivityResult method
    }

    fun loginWithFacebook() {
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    viewModelScope.launch {
                        _username.value = "facebook_user"
                        _isLoggedIn.value = true
                        saveLoginState("facebook_user")
                    }
                }

                override fun onCancel() {
                    // Handle cancelled login
                }

                override fun onError(error: FacebookException) {
                    // Handle login error
                }
            })

        LoginManager.getInstance().logInWithReadPermissions(getApplication(), listOf("public_profile", "email"))
    }

     */

    private fun saveLoginState(username: String) {
        sharedPreferences.edit().putString("username", username).apply()
    }

    private fun clearLoginState() {
        sharedPreferences.edit().remove("username").apply()
    }

    fun logout() {
        viewModelScope.launch {
            firebaseAuth.signOut()
            _username.value = null
            _isLoggedIn.value = false
            clearLoginState()
        }
    }
}

