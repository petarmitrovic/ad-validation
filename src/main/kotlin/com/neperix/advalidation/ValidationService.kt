package com.neperix.advalidation

import com.neperix.advalidation.notification.NotificationService
import com.neperix.advalidation.notification.ValidationResult
import com.neperix.advalidation.storage.MaterialsStorage
import java.util.UUID

class ValidationService(
    private val storage: MaterialsStorage,
    private val channel: NotificationService
) {
    fun process(materialUuid: UUID) {
        val material = storage.fetchMaterial(materialUuid)

        val result = if (material.size > 100 * 1024)
            ValidationResult(true)
        else
            ValidationResult(false, "Material should be larger than 100 kB")

        channel.send(result)
    }
}
