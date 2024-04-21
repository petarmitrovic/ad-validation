package com.neperix.advalidation

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import java.util.UUID

private val logger = LoggerFactory.getLogger(AdMaterialListener::class.java)

class AdMaterialListener(
    private val validationService: ValidationService
) {

    @KafkaListener(topics = ["#{'\${spring.kafka.topics}'.split(',')}"])
    fun onAdMaterialUploaded(message: String) {
        val materialUuid = UUID.fromString(message)
        logger.info(message)
        validationService.process(materialUuid)
    }
}
