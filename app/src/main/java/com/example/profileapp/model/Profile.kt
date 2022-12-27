package com.example.profileapp.model

import com.example.profileapp.R

data class Profile(
    val isAdmin: Boolean = false,
    val firstName: String = "Mahabbat",
    val lastName: String = "Jomardli",
    val email: String = "comerdlim@gmail.com",
    val telephone: String = "+994503181063",
    val gender: String = "Female",
    val avatarUrl: Int = R.drawable.profile,
    val customerNo: String = "3181063",
)