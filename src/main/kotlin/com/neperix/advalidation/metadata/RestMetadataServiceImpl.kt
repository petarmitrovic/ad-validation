package com.neperix.advalidation.metadata

import java.util.UUID

/**
 * An implementation of MetadataService which gets the data from other service.
 */
class RestMetadataServiceImpl : MetadataService {
    override fun fetchMaterialMetadata(uuid: UUID): AdMaterialMetadata {
        TODO("Not yet implemented")
    }
}
