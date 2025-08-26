package com.andhikaaw.androiddevelopertest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andhikaaw.androiddevelopertest.data.model.District
import com.andhikaaw.androiddevelopertest.data.model.Province
import com.andhikaaw.androiddevelopertest.data.model.Regency
import com.andhikaaw.androiddevelopertest.data.model.Village

@Database(
    entities = [Province::class, Regency::class, District::class, Village::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun regionDao(): RegionDao
}
