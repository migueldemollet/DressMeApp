package com.migueldemollet.dressmeapp.screens.logIn

data class LoginState(
    val email: String = "",
    val password: String = "",
    val successLogin: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
