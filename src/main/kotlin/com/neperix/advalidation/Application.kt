package com.neperix.advalidation

import com.neperix.advalidation.metadata.LocalMetadataStorage
import com.neperix.advalidation.metadata.MetadataService
import com.neperix.advalidation.notification.KafkaNotificationService
import com.neperix.advalidation.notification.NotificationService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaTemplate

@SpringBootApplication
class AdMaterialValidationApplication {

    @Bean
    fun localMetadataService(): MetadataService = LocalMetadataStorage()

    @Bean
    fun notificationChannel(
        template: KafkaTemplate<String, String>,
        @Value("\${notifications.kafka.topic}") topicName: String
    ): NotificationService {
        return KafkaNotificationService(template, topicName)
    }

    @Bean
    fun service(storage: MetadataService, channel: NotificationService) = ValidationService(storage, channel)

    @Bean
    fun adMaterialLisener(service: ValidationService): AdMaterialListener {
        return AdMaterialListener(service)
    }
}

fun main(args: Array<String>) {
    runApplication<AdMaterialValidationApplication>(*args)
}
