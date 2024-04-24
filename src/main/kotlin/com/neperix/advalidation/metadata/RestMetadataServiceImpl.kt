package com.neperix.advalidation.metadata

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.neperix.advalidation.rules.RulesServiceException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.util.UUID

/**
 * An implementation of MetadataService which gets the data from other service.
 */
class RestMetadataServiceImpl(
    private val baseUrl: String,
    private val mapper: ObjectMapper = ObjectMapper().registerKotlinModule()
) : MetadataService {
    private val httpClient = HttpClient.newHttpClient()

    override fun fetchMaterialMetadata(uuid: UUID): AdMaterialMetadata {
        try {
            val request = HttpRequest.newBuilder(URI.create("$baseUrl/materials/$uuid")).build()
            val response = httpClient.send(request, BodyHandlers.ofString())

            if (response.statusCode() != 200) {
                // TODO handle error
            }

            return mapper.readValue(response.body())
        } catch (ex: Exception) {
            // Handle other exceptions
            throw RulesServiceException("Failed to fetch rules: ${ex.message}", ex)
        }
    }
}
