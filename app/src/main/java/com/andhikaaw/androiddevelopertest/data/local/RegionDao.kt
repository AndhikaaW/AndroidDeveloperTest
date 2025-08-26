package com.andhikaaw.androiddevelopertest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andhikaaw.androiddevelopertest.data.model.District
import com.andhikaaw.androiddevelopertest.data.model.Province
import com.andhikaaw.androiddevelopertest.data.model.Regency
import com.andhikaaw.androiddevelopertest.data.model.Village
import kotlinx.coroutines.flow.Flow

@Dao
interface RegionDao {
    // Province
    @Query("SELECT * FROM provinces")
    fun getProvinces(): Flow<List<Province>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProvinces(provinces: List<Province>)

    // Regency
    @Query("SELECT * FROM regencies WHERE province_id = :provinceId")
    fun getRegencies(provinceId: String): Flow<List<Regency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegencies(regencies: List<Regency>)

    // District
    @Query("SELECT * FROM districts WHERE regency_id = :regencyId")
    fun getDistricts(regencyId: String): Flow<List<District>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDistricts(districts: List<District>)

    // Village
    @Query("SELECT * FROM villages WHERE district_id = :districtId")
    fun getVillages(districtId: String): Flow<List<Village>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVillages(villages: List<Village>)
}
