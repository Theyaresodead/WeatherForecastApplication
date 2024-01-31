package com.example.weatherforecast.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_table")
data class Units(
         @NonNull
         @PrimaryKey
         @ColumnInfo(name="units")
         val units:String
)
