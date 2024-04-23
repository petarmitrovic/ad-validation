package com.neperix.advalidation.metadata

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File
import java.util.UUID

/**
 * An implementation of MetadataService that fetches the predefined metadata from local file system.
 */
class LocalMetadataStorage : MetadataService {

    private val parentDir = File(this.javaClass.getResource("/metadata")!!.path)
    private val objectMapper = ObjectMapper().registerKotlinModule()

    override fun fetchMaterialMetadata(uuid: UUID): AdMaterialMetadata {
        val file = File(parentDir, "$uuid.json")
        if (!file.exists()) {
            throw IllegalArgumentException("Could not find file $uuid in $parentDir")
        }

        return objectMapper.readValue(file, AdMaterialMetadata::class.java)
    }
}
