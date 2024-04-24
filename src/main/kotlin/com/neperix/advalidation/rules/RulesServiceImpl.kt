package com.neperix.advalidation.rules

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.UUID

/**
 * An implementation of RulesService which gets the rules from products service.
 */
class RulesServiceImpl(
    private val baseUrl: String,
    private val mapper: ObjectMapper = ObjectMapper().registerKotlinModule()
) : RulesService {
    private val httpClient = HttpClient.newHttpClient()

    override fun getRules(productUUID: UUID): List<Rule> {
        try {
            val request = HttpRequest.newBuilder(URI.create("$baseUrl/products/$productUUID")).build()
            val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

            if (response.statusCode() != 200) {
                // TODO handle error
            }

            return mapper.readValue<List<ProductRule>>(response.body()).map { it.toRule() }
        } catch (ex: Exception) {
            // Handle other exceptions
            throw RulesServiceException("Failed to fetch rules: ${ex.message}", ex)
        }
    }

    /**
     * DTO to capture calling service response.
     */
    internal data class ProductRule(
        val productId: UUID
    ) {

        /**
         * Mapper function that builds internal rule based on the received DTO.
         */
        fun toRule(): Rule {
            TODO("Not yet implemented")
        }
    }
}
