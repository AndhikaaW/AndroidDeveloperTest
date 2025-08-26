package com.andhikaaw.androiddevelopertest.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andhikaaw.androiddevelopertest.data.model.District
import com.andhikaaw.androiddevelopertest.data.model.Province
import com.andhikaaw.androiddevelopertest.data.model.Regency
import com.andhikaaw.androiddevelopertest.data.model.Village
import com.andhikaaw.androiddevelopertest.data.repository.RegionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RegionViewModel(private val repo: RegionRepository) : ViewModel() {

    val provinces: StateFlow<List<Province>> =
        repo.getProvinces().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun loadProvinces() = viewModelScope.launch { repo.refreshProvinces() }

    fun regencies(provinceId: String): Flow<List<Regency>> = repo.getRegencies(provinceId)
    fun loadRegencies(provinceId: String) = viewModelScope.launch {
        repo.refreshRegencies(provinceId)
    }

    fun districts(regencyId: String): Flow<List<District>> = repo.getDistricts(regencyId)
    fun loadDistricts(regencyId: String) = viewModelScope.launch {
        repo.refreshDistricts(regencyId)
    }

    fun villages(districtId: String): Flow<List<Village>> = repo.getVillages(districtId)
    fun loadVillages(districtId: String) = viewModelScope.launch {
        repo.refreshVillages(districtId)
    }
}
