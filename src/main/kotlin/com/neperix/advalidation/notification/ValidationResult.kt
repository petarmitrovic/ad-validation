package com.neperix.advalidation.notification

data class ValidationResult(
    val success: Boolean,
    val error: String? = null
)
