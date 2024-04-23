package com.neperix.advalidation

import com.neperix.advalidation.metadata.LocalMetadataStorage
import com.neperix.advalidation.metadata.MetadataService
import com.neperix.advalidation.notification.KafkaNotificationService
import com.neperix.advalidation.notification.NotificationService
import com.neperix.advalidation.rules.DummyRulesService
import com.neperix.advalidation.rules.RulesService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaTemplate

@SpringBootApplication
class AdMaterialValidationApplication {

    // TODO Should be available only under the development profile
    @Bean
    fun metadataService(): MetadataService = LocalMetadataStorage()

    @Bean
    fun notificationService(
        template: KafkaTemplate<String, String>,
        @Value("\${notifications.kafka.topic}") topicName: String
    ): NotificationService {
        return KafkaNotificationService(template, topicName)
    }

    // TODO Should be available only under the development profile
    @Bean
    fun rulesService(): RulesService = DummyRulesService()

    @Bean
    fun service(
        metadataService: MetadataService,
        rulesService: RulesService,
        notificationService: NotificationService
    ) = ValidationService(
        metadataService,
        rulesService,
        notificationService
    )

    @Bean
    fun adMaterialLisener(service: ValidationService): AdMaterialListener {
        return AdMaterialListener(service)
    }
}

fun main(args: Array<String>) {
    runApplication<AdMaterialValidationApplication>(*args)
}
