package com.example.petheart.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.petheart.Memory
import java.util.*

@Dao
interface MemoryDao{

    @Query("SELECT * FROM memory")
    fun getMemories(): LiveData<List<Memory>>

    @Query("SELECT * FROM memory WHERE id = (:id)")
    fun getMemory(id: UUID):LiveData<Memory?>

    @Query(value = "SELECT * FROM memory WHERE isFavorite != 0")
    fun getFavorites() : LiveData<List<Memory>>

    @Update
    fun updateMemory(memory: Memory)

    @Insert
    fun addMemory(memory: Memory)

    @Delete
    fun deleteMemory(memory: Memory)
}