package com.example.petheart

import android.app.Application

class PetHeartApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        MemoryRepository.initialize(this)
    }
}