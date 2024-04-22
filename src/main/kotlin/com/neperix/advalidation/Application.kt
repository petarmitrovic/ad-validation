package com.neperix.advalidation

import com.neperix.advalidation.notification.KafkaNotificationService
import com.neperix.advalidation.notification.NotificationService
import com.neperix.advalidation.storage.LocalMaterialsStorage
import com.neperix.advalidation.storage.MaterialsStorage
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaTemplate

@SpringBootApplication
class AdMaterialValidationApplication {

    @Bean
    fun fileStorage(@Value("\${materials.storage.local.path}") dir: String): MaterialsStorage = LocalMaterialsStorage(dir)

    @Bean
    fun notificationChannel(
        template: KafkaTemplate<String, String>,
        @Value("\${notifications.kafka.topic}") topicName: String
    ): NotificationService {
        return KafkaNotificationService(template, topicName)
    }

    @Bean
    fun service(storage: MaterialsStorage, channel: NotificationService) = ValidationService(storage, channel)

    @Bean
    fun adMaterialLisener(service: ValidationService): AdMaterialListener {
        return AdMaterialListener(service)
    }
}

fun main(args: Array<String>) {
    runApplication<AdMaterialValidationApplication>(*args)
}
