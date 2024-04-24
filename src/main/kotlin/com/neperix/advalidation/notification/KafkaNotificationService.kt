package com.neperix.advalidation.notification

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.neperix.advalidation.ValidationResult
import org.springframework.kafka.core.KafkaTemplate

class KafkaNotificationService(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val topicName: String,
    private val mapper: ObjectMapper = ObjectMapper().registerKotlinModule()
) : NotificationService {

    override fun send(result: ValidationResult) {
        kafkaTemplate.send(topicName, mapper.writeValueAsString(result))
    }
}
