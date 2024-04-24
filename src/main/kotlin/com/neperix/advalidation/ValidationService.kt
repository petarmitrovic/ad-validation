package com.neperix.advalidation

import com.neperix.advalidation.metadata.MetadataService
import com.neperix.advalidation.notification.NotificationService
import com.neperix.advalidation.rules.RulesService

class ValidationService(
    private val metadataService: MetadataService,
    private val rulesService: RulesService,
    private val notificationService: NotificationService
) {
    fun process(command: ValidationCommand) {
        // TODO the follwoing 2 could be done in parallel; use coroutines
        val materialMetadata = metadataService.fetchMaterialMetadata(command.materialUUID)
        val rulesChecks = rulesService.getRules(command.productUUID)
            .map { it.check(materialMetadata) }

        val result = ValidationResult(
            successes = rulesChecks.filter { it.success }.map { it.message },
            failures = rulesChecks.filterNot { it.success }.map { it.message }
        )

        notificationService.send(result)
    }
}
