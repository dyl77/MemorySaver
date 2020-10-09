package com.example.petheart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import java.io.File

class MemoryListViewModel : ViewModel() {
    private val memoryRepository = MemoryRepository.get()
    val memoryListLiveData = memoryRepository.getMemories()

    fun addMemory(memory: Memory){
        memoryRepository.addMemory(memory)
    }

    fun getFavorites(): LiveData<List<Memory>> {
       return memoryRepository.getFavorites()
    }

}