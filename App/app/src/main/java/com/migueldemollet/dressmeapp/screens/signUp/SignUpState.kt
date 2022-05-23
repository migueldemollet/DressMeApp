package com.migueldemollet.dressmeapp.screens.signUp

data class SignUpState(
    val successRegister: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
