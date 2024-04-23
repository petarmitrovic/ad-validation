package com.neperix.advalidation.rules

import com.neperix.advalidation.metadata.AdMaterialMetadata

class FileSizeRule(
    private val range: IntRange
) : Rule {

    constructor(max: Int) : this(0..max)

    override fun check(metadata: AdMaterialMetadata): Result {
        return Result(
            success = metadata.size in range,
            message = "Found size (${metadata.size}); Expected within range $range)"
        )
    }
}
