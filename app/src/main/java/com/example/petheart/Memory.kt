package com.example.petheart

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import java.util.Date

@Entity
data class Memory(@PrimaryKey val id: UUID = UUID.randomUUID(), var title: String = "", var date: Date = Date(), var isFavorite: Boolean = false) {
    val photoFileName get() = "IMG_$id.jpg"
}