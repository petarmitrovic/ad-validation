package com.neperix.advalidation

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener

private val logger = LoggerFactory.getLogger(AdMaterialListener::class.java)

class AdMaterialListener(
    private val validationService: ValidationService
) {

    @KafkaListener(topics = ["#{'\${spring.kafka.topics}'.split(',')}"])
    fun onAdMaterialUploaded(command: ProcessMaterialCommand) {
        logger.info("Recieved command: $command")
        validationService.process(command)
    }
}
