package com.neperix.advalidation

import java.util.UUID

data class ProcessMaterialCommand(
    val materialUUID: UUID,
    val productUUID: UUID,
)
