package com.andhikaaw.androiddevelopertest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "villages")
data class Village(
    @PrimaryKey val id: String,
    val district_id: String,
    val name: String
)

data class VillageResponse(
    val id: String,
    val district_id: String,
    val name: String
)
