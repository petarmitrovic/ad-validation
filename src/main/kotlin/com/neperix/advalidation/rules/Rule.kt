package com.neperix.advalidation.rules

import com.neperix.advalidation.metadata.AdMaterialMetadata

interface Rule {

    fun check(metadata: AdMaterialMetadata): Result
}
