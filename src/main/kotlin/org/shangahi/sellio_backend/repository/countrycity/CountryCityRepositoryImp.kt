package org.shangahi.sellio_backend.repository.countrycity

import org.shangahi.sellio_backend.service.exception.CitiesNotFoundException
import org.shangahi.sellio_backend.service.model.CitiesModel
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestClientResponseException
import org.springframework.web.client.exchange

@Repository
class CountryCityRepositoryImp(
    restTemplateBuilder: RestTemplateBuilder
) : CountryCityRepository {

    private val restTemplate = restTemplateBuilder.build()

    override fun getCitiesByCountryIso2(iso2: String): CitiesModel {
        return try {
            val response = request(iso2 = iso2)

            if (!response.statusCode.is2xxSuccessful) {
                throw CitiesNotFoundException("${response.statusCode}")
            }

            response.body ?: throw CitiesNotFoundException("Empty Cities Response")
        } catch (e: RestClientResponseException) {
            throw CitiesNotFoundException(e.message)
        }.toEntity()
    }

    private fun request(
        path: String = "https://countriesnow.space/api/v0.1/countries/states",
        iso2: String
    ): ResponseEntity<CountryCityDto> {
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }

        val requestEntity = HttpEntity(
            CitiesRequest(iso2),
            headers
        )

        return restTemplate.exchange<CountryCityDto>(
            url = path,
            method = HttpMethod.POST,
            requestEntity = requestEntity,
        )
    }

}