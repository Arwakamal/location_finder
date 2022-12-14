package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import com.udacity.project4.locationreminders.data.dto.ReminderDTO

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Test

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest
class RemindersDaoTest {


    lateinit var database: RemindersDatabase

    @Before
    fun initDatabase()
    {
        database=Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),RemindersDatabase::class.java).build()
    }

    @Test
    fun testDatabase()= runBlockingTest {
        // GIVEN: the user insert  location and title..etc.
        val item =ReminderDTO("title", "description", "location", 0.0, 0.0)

        // WHEN: the app get location by id from the database to show it to the user.
        runBlocking {
            database.reminderDao().saveReminder(item)

            val result = database.reminderDao().getReminderById(item.id)
           // THEN - the data that we got it from database should be same as expected.
            assertThat(item.id, `is`(result!!.id))
            assertThat(item.description, `is`(result!!.description))
            assertThat(item.title, `is`(result!!.title))
            assertThat(item.location, `is`(result!!.location))
            assertThat(item.latitude, `is`(result!!.latitude))
            assertThat(item.longitude, `is`(result!!.longitude))
        }

    }
    //close database
    @After
    fun colseDatabase()
    {
        database.close()
    }

}