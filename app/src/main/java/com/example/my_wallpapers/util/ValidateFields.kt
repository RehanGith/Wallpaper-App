package com.example.my_wallpapers.util

import android.util.Patterns

fun checkValidationForEmail(email : String) : RegisterValidation{
    if(email.isEmpty()){
        return RegisterValidation.failure("Email is empty")
    }
    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return RegisterValidation.failure("Wrong email format")
    }
    return RegisterValidation.success
}

fun checkValidationForPassword(password : String) : RegisterValidation{
    if(password.isEmpty()){
        return RegisterValidation.failure("Password is empty")
    }
    if(password.length < 6) {
        return RegisterValidation.failure("Password length is less than 6")
    }
    return RegisterValidation.success
}