package com.andhikaaw.androiddevelopertest.data.remote

import com.andhikaaw.androiddevelopertest.data.model.DistrictResponse
import com.andhikaaw.androiddevelopertest.data.model.ProvinceResponse
import com.andhikaaw.androiddevelopertest.data.model.RegencyResponse
import com.andhikaaw.androiddevelopertest.data.model.VillageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("provinces.json")
    suspend fun getProvinces(): List<ProvinceResponse>

    @GET("regencies/{provinceId}.json")
    suspend fun getRegencies(@Path("provinceId") provinceId: String): List<RegencyResponse>

    @GET("districts/{regencyId}.json")
    suspend fun getDistricts(@Path("regencyId") regencyId: String): List<DistrictResponse>

    @GET("villages/{districtId}.json")
    suspend fun getVillages(@Path("districtId") districtId: String): List<VillageResponse>
}
