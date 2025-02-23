package com.example.my_wallpapers.util

sealed class RegisterValidation {
    object success : RegisterValidation()
    class failure(val message: String) : RegisterValidation()
}

data class RegisterValidationFields(
    val email:RegisterValidation,
    val password: RegisterValidation
)