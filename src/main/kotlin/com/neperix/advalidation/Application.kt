package com.neperix.advalidation

import com.neperix.advalidation.materials.LocalMaterialsStorage
import com.neperix.advalidation.materials.MaterialsStorage
import com.neperix.advalidation.notification.KafkaChannel
import com.neperix.advalidation.notification.NotificationChannel
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
    ): NotificationChannel {
        return KafkaChannel(template, topicName)
    }

    @Bean
    fun service(storage: MaterialsStorage, channel: NotificationChannel) = ValidationService(storage, channel)

    @Bean
    fun adMaterialLisener(service: ValidationService): AdMaterialListener {
        return AdMaterialListener(service)
    }
}

fun main(args: Array<String>) {
    runApplication<AdMaterialValidationApplication>(*args)
}
