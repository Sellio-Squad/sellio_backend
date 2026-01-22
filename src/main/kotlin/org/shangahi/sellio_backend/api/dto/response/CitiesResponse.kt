package org.shangahi.sellio_backend.api.dto.response

import org.shangahi.sellio_backend.service.model.CitiesModel

data class CitiesResponse(
    val cities: List<String>
)

fun CitiesModel.toResponse(): CitiesResponse = CitiesResponse(
    cities = cities
)