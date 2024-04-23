package com.neperix.advalidation

import com.neperix.advalidation.metadata.MetadataService
import com.neperix.advalidation.notification.NotificationService
import com.neperix.advalidation.notification.ValidationResult
import java.util.UUID

class ValidationService(
    private val metadataService: MetadataService,
    private val notificationService: NotificationService
) {
    fun process(materialUuid: UUID) {
        val material = metadataService.fetchMaterialMetadata(materialUuid)

        val result = if (material.size > 100 * 1024)
            ValidationResult(true)
        else
            ValidationResult(false, "Material should be larger than 100 kB")

        notificationService.send(result)
    }
}
