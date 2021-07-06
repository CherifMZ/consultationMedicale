package com.example.consultation.roomDao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.consultation.data.data.models.Conseil


@Database(entities = arrayOf(Conseil::class),version = 1)

abstract class AppDataBase : RoomDatabase() {
    abstract fun getConseilDao(): ConseilDao
}