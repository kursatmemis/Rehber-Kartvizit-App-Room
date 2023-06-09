package com.example.rehberimapp.configs

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rehberimapp.dao.PersonDao
import com.example.rehberimapp.models.Person
import java.io.Serializable

@Database(entities = [Person::class], version = 1)
abstract class AppDataBase : RoomDatabase(), Serializable {

    abstract fun personDao(): PersonDao

    companion object {
        var INSTANCE: AppDataBase? = null

        fun accessDataBase(context: Context): AppDataBase? {
            if (INSTANCE == null) {
                synchronized(AppDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "appDataBase"
                    ).build()
                }
            }

            return INSTANCE
        }
    }

}