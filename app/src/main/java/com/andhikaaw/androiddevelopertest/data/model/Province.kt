package com.andhikaaw.androiddevelopertest.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "provinces")

data class Province(
    @PrimaryKey val id: String,
    val name: String
)

data class ProvinceResponse(
    val id: String,
    val name: String
)
