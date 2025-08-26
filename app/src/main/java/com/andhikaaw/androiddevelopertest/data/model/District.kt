package com.andhikaaw.androiddevelopertest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "districts")
data class District(
    @PrimaryKey val id: String,
    val regency_id: String,
    val name: String
)

data class DistrictResponse(
    val id: String,
    val regency_id: String,
    val name: String
)
