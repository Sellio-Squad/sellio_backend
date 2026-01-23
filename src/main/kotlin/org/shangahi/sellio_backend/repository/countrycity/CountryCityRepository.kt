package org.shangahi.sellio_backend.repository.countrycity

import org.shangahi.sellio_backend.service.model.CitiesModel

interface CountryCityRepository {
    fun getCitiesByCountryIso2(iso2: String): CitiesModel
}