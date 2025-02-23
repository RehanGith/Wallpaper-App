package com.example.my_wallpapers.model

data class User(
    val firstName: String,
    val secondName: String,
    val email: String,
    val imagePath: String = ""
)