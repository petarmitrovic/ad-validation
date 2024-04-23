package com.neperix.advalidation

data class ValidationResult(
    val successes: List<String>,
    val failures: List<String>
)
