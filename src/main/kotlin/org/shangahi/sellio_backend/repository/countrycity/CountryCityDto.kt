package org.shangahi.sellio_backend.repository.countrycity

import com.fasterxml.jackson.annotation.JsonProperty
import org.shangahi.sellio_backend.service.model.CitiesModel

data class CitiesRequest(
    val iso2: String
)
data class CountryCityDto(
    val error: Boolean?,
    val msg: String?,
    val data: CitiesDto
)

data class CitiesDto(
    val name: String?,
    val iso3: String?,
    val iso2: String?,
    @param:JsonProperty("states")
    val cities: List<CityDto>
)

data class CityDto (
    val name: String?,
    @param:JsonProperty("state_code")
    val cityCode: String?
)

fun CountryCityDto.toEntity(): CitiesModel {
    return CitiesModel(
        cities = data.cities.mapNotNull { it.name }
    )
}