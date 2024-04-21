package com.neperix.advalidation.notification

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate

class KafkaChannel(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val topicName: String
) : NotificationChannel {

    private val mapper = ObjectMapper()

    override fun send(result: ValidationResult) {
        val message = mapper.writeValueAsString(result)
        kafkaTemplate.send(topicName, message)
    }
}
