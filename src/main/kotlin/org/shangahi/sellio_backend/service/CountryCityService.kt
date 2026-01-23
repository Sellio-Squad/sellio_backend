package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.repository.countrycity.CountryCityRepository
import org.shangahi.sellio_backend.service.model.CitiesModel
import org.springframework.stereotype.Service

@Service
class CountryCityService(
    private val repository: CountryCityRepository
) {
    fun getCitiesByCountryIso2(iso2: String): CitiesModel {
        val cities = repository.getCitiesByCountryIso2(iso2)
            .cities.map { (it.mapCities()) }

        return CitiesModel(cities)
    }

    private fun String.mapCities(): String {
        return replace("Governorate", "")
            .replace("Province", "")
            .replace("District", "")
            .replace("County", "")
            .replace("Region", "")
            .replace("Emirate", "")
            .replace("Prefecture", "")
            .replace("Territory", "")
            .replace("Division", "")
            .replace("State", "")
            .trim()
    }
}