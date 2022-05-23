package com.migueldemollet.dressmeapp.screens.signUp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.migueldemollet.dressmeapp.R
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    val state: MutableState<SignUpState> = mutableStateOf(SignUpState())

    fun signUp(email: String, password: String) {
        val errorMessage = if(email.isBlank() || password.isBlank()) {
            "The fields must not be empty"
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            "Please enter a valid E-Mail"
        } else {
            null
        }

        errorMessage?.let {
            state.value = state.value.copy(errorMessage = it)
            return
        }

        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    state.value = state.value.copy(isLoading = false)
                    if (task.isSuccessful) {
                        state.value = state.value.copy(errorMessage = null)
                        state.value = state.value.copy(successRegister = true)
                    } else {
                        state.value = state.value.copy(errorMessage = task.exception?.message)
                        state.value = state.value.copy(successRegister = false)
                    }
                }
        }
    }

    fun signUpGoogle(context: Context) {
        state.value = state.value.copy(isLoading = true)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("786058273769-ujvh6qd64pvjbj5tu9dcj79eamsg41c3.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val client = GoogleSignIn.getClient(context, gso)

        val signInIntent: Intent = client.signInIntent
        (context as Activity).startActivityForResult(signInIntent, 2)
    }

    fun hideErrorDialog() {
        state.value = state.value.copy(errorMessage = null)
    }

    fun handleSignUpResult(accountTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = accountTask.getResult(ApiException::class.java)
            account?.idToken?.let { token ->
                val auth = FirebaseAuth.getInstance()
                val credential = GoogleAuthProvider.getCredential(token, null)

                auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        state.value = state.value.copy(isLoading = false)
                        state.value = state.value.copy(errorMessage = null)
                        state.value = state.value.copy(successRegister = true)
                    } else {
                        state.value = state.value.copy(isLoading = false)
                        state.value = state.value.copy(errorMessage = "Invalid email or password")
                        state.value = state.value.copy(successRegister = false)
                    }
                }
            }
        } catch (e: ApiException) {
            state.value = state.value.copy(isLoading = false)
            state.value = state.value.copy(errorMessage = "Error Sign up with Google")
            state.value = state.value.copy(successRegister = false)
            return
        }

    }
}