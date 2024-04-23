package com.neperix.advalidation.rules

import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import java.util.UUID

/**
 * An implementation of RulesService which gets the rules from products service.
 */
class RulesServiceImpl(
    private val webClient: WebClient
) : RulesService {

    override fun getRules(productUUID: UUID): List<Rule> {
        val url = "$productUUID" // Replace with the actual URL of the rules service
        try {
            val response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(List::class.java)
                .block() as List<ProductRule>? ?: emptyList()

            return response.mapNotNull { it.toRule() }
        } catch (ex: WebClientResponseException) {
            // Handle HTTP client errors (e.g., 404, 500)
            throw RulesServiceException("Failed to fetch rules: ${ex.message}", ex)
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
