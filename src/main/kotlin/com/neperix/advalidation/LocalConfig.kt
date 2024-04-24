package com.neperix.advalidation

import com.fasterxml.jackson.databind.ObjectMapper
import com.neperix.advalidation.metadata.MetadataService
import com.neperix.advalidation.metadata.RestMetadataServiceImpl
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile

@Profile("local")
@Configuration
class LocalConfig {

    @Primary
    @Bean
    fun localMetadataService(
        @Value("\${materials.metadata.base-url}") baseUrl: String,
        objectMapper: ObjectMapper
    ): MetadataService {
        return RestMetadataServiceImpl(baseUrl, objectMapper)
    }
}
