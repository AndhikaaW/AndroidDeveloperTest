package com.andhikaaw.androiddevelopertest.data.repository

import com.andhikaaw.androiddevelopertest.data.local.AppDatabase
import com.andhikaaw.androiddevelopertest.data.model.District
import com.andhikaaw.androiddevelopertest.data.model.Province
import com.andhikaaw.androiddevelopertest.data.model.Regency
import com.andhikaaw.androiddevelopertest.data.model.Village
import com.andhikaaw.androiddevelopertest.data.remote.ApiService

class RegionRepository(
    private val api: ApiService,
    private val db: AppDatabase
) {
    private val dao = db.regionDao()

    fun getProvinces() = dao.getProvinces()
    suspend fun refreshProvinces() {
        val data = api.getProvinces().map { Province(it.id, it.name) }
        dao.insertProvinces(data)
    }

    fun getRegencies(provinceId: String) = dao.getRegencies(provinceId)
    suspend fun refreshRegencies(provinceId: String) {
        val data = api.getRegencies(provinceId).map { Regency(it.id, it.province_id, it.name) }
        dao.insertRegencies(data)
    }

    fun getDistricts(regencyId: String) = dao.getDistricts(regencyId)
    suspend fun refreshDistricts(regencyId: String) {
        val data = api.getDistricts(regencyId).map { District(it.id, it.regency_id, it.name) }
        dao.insertDistricts(data)
    }

    fun getVillages(districtId: String) = dao.getVillages(districtId)
    suspend fun refreshVillages(districtId: String) {
        val data = api.getVillages(districtId).map { Village(it.id, it.district_id, it.name) }
        dao.insertVillages(data)
    }
}

