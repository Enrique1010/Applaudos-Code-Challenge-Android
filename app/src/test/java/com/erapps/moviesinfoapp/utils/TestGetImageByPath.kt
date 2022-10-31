package com.erapps.moviesinfoapp.utils

import com.erapps.moviesinfoapp.BuildConfig
import org.junit.Assert
import org.junit.Test

class TestGetImageByPath {

    @Test
    fun `test if string return a path with image base url`() {
        val path = "image.jpg"
        val finalPath = path.getImageByPath()

        Assert.assertEquals(
            "${BuildConfig.The_Movie_DB_Image_Base_URL}$path",
            finalPath
        )
    }
}