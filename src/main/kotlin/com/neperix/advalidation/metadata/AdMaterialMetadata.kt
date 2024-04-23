package com.neperix.advalidation.metadata

data class AdMaterialMetadata(
    val format: String,
    val size: Long,
    val resolution: Int,
    val width: Int,
    val height: Int,
    val duration: Int
)
