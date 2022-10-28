package com.erapps.moviesinfoapp.utils

import com.erapps.moviesinfoapp.BuildConfig

fun String.getImageByPath(): String {
    val baseUrl = BuildConfig.The_Movie_DB_Image_Base_URL
    return "$baseUrl${this}"
}