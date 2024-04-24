package com.neperix.advalidation

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener

private val logger = LoggerFactory.getLogger(AdMaterialListener::class.java)

// TODO Use message converters
class AdMaterialListener(
    private val validationService: ValidationService,
    private val mapper: ObjectMapper = ObjectMapper().registerKotlinModule()
) {

    @KafkaListener(topics = ["#{'\${spring.kafka.topics}'.split(',')}"])
    fun onAdMaterialUploaded(message: String) {
        logger.info("Received message: $message")
        validationService.process(mapper.readValue(message))
    }
}
