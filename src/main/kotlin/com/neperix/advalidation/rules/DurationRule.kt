package com.neperix.advalidation.rules

import com.neperix.advalidation.metadata.AdMaterialMetadata

class DurationRule(
    private val max: Int,
    private val min: Int = 0
) : Rule {

    override fun check(metadata: AdMaterialMetadata): Result {
        return Result(
            success = metadata.duration in min..max,
            message = "Found duration ${metadata.duration}; Expected between $min and $max"
        )
    }
}
