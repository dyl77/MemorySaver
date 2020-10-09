package com.example.petheart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.io.File
import java.util.*

class MemoryDetailViewModel() : ViewModel() {

    private val memoryRepository = MemoryRepository.get()
    private val memoryIdLiveData = MutableLiveData<UUID>()

    var memoryLiveData: LiveData<Memory?> =
        Transformations.switchMap(memoryIdLiveData){ memoryId->
            memoryRepository.getMemory(memoryId)
        }

    fun loadMemory(memoryId:UUID){
        memoryIdLiveData.value = memoryId
    }

    fun saveMemory(memory: Memory){
        memoryRepository.updateMemory(memory)
    }

    fun getPhotoFile(memory: Memory): File {
        return memoryRepository.getPhotoFile(memory)
    }

    fun deleteMemory(memory: Memory){
        memoryRepository.deleteMemory(memory)
    }
}