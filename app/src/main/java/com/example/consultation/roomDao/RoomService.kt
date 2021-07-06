package com.example.consultation.roomDao

import android.content.Context
import androidx.room.Room

object RoomService {
    lateinit var context: Context

    val appDataBase: AppDataBase by lazy {
        Room.databaseBuilder(context, AppDataBase::class.java,"conseildb").allowMainThreadQueries().build()
    }

}