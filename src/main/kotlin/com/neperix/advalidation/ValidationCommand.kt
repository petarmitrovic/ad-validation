package com.neperix.advalidation

import java.util.UUID

data class ValidationCommand(
    val materialUUID: UUID,
    val productUUID: UUID
)
