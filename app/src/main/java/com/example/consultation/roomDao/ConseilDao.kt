package com.example.consultation.roomDao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.consultation.data.data.models.Conseil

@Dao
interface ConseilDao {

    @Query("select * from conseils ")
    fun getConseil():List<Conseil>

    @Query("select * from conseils where isSynchronized=0")
    fun getConseilToSynchronize():List<Conseil>

    @Insert
    fun addConseil(vararg conseil: Conseil)

    @Update
    fun updateConseil(conseil: Conseil)
}