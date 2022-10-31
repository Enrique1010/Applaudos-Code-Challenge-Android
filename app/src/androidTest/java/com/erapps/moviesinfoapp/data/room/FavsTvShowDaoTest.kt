package com.erapps.moviesinfoapp.data.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.erapps.moviesinfoapp.data.room.daos.FavsTvShowsDao
import com.erapps.moviesinfoapp.data.room.entities.FavoriteTvShow
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class FavsTvShowDaoTest {

    private lateinit var dataBase: MoviesInfoAppDataBase
    private lateinit var dao: FavsTvShowsDao

    @Before
    fun setup() {
        dataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesInfoAppDataBase::class.java
        ).allowMainThreadQueries().build()

        dao = dataBase.favsTvShowsDao()
    }

    @After
    fun teardown() {
        dataBase.close()
    }

    @Test
    fun testInsertAndGetFavTvShow() = runTest {

        for (i in 0..5) {
            val tvShow = FavoriteTvShow(
                first_air_date = "20-01-2022",
                id = 5050 + i,
                name = "Cars $i",
                overview = "",
                poster_path = "",
                vote_average = 8.5
            )

            dao.insertFavTvShow(tvShow)
        }

        val tvShows = dao.getFavsTvShows()

        Truth.assertThat(tvShows[1].name).contains("Cars 1")
    }

    @Test
    fun testGetFavoriteTvShowId() = runTest {
        for (i in 0..5) {
            val tvShow = FavoriteTvShow(
                first_air_date = "20-01-2022",
                id = 5050 + i,
                name = "Cars $i",
                overview = "",
                poster_path = "",
                vote_average = 8.5
            )

            dao.insertFavTvShow(tvShow)
        }

        val dbId = dao.getFavId(5051)

        Truth.assertThat(dbId).isEqualTo(5051)
    }

    @Test
    fun testDeleteFavoriteTvShow() = runTest {
        for (i in 0..5) {
            val tvShow = FavoriteTvShow(
                first_air_date = "20-01-2022",
                id = 5050 + i,
                name = "Cars $i",
                overview = "",
                poster_path = "",
                vote_average = 8.5
            )

            dao.insertFavTvShow(tvShow)
        }

        dao.deleteFavTvShow(5051)

        val tvShows = dao.getFavsTvShows()

        Truth.assertThat(tvShows.size).isEqualTo(5)
    }
}