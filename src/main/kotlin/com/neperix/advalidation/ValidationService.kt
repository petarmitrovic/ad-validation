package com.neperix.advalidation

import com.neperix.advalidation.materials.MaterialsStorage
import com.neperix.advalidation.notification.NotificationChannel
import com.neperix.advalidation.notification.ValidationResult
import java.util.UUID

class ValidationService(
    private val storage: MaterialsStorage,
    private val channel: NotificationChannel
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
