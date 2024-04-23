package com.neperix.advalidation.metadata

import java.util.UUID

/**
 * An abstraction used to fetch material metadata.
 */
interface MetadataService {

    fun fetchMaterialMetadata(uuid: UUID): AdMaterialMetadata
}
