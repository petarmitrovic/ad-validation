package com.neperix.advalidation.rules

import com.neperix.advalidation.metadata.AdMaterialMetadata

class ResolutionRule(
    private val heightRange: IntRange,
    private val widthRange: IntRange,
    private val dpiRange: IntRange,
) : Rule {
    override fun check(metadata: AdMaterialMetadata): Result {
        return Result(
            success = metadata.height in heightRange && metadata.width in widthRange && metadata.resolution in dpiRange,
            message = "Found $metadata; Expected height: $heightRange, width: $widthRange; resolution: $dpiRange"
        )
    }
}
