package com.andhikaaw.androiddevelopertest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "regencies")
data class Regency(
    @PrimaryKey val id: String,
    val province_id: String,
    val name: String
)

data class RegencyResponse(
    val id: String,
    val province_id: String,
    val name: String
)
