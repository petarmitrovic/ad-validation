package com.neperix.advalidation.materials

import com.neperix.advalidation.metadata.LocalMetadataStorage
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

internal class LocalMaterialsStorageTest {

    private val storageDir = this.javaClass.getResource("/metadata")!!.path

    @Test
    fun `it should fetch ad material metadata if present`() {
        // given
        val storage = LocalMetadataStorage()

        // when
        val material = storage.fetchMaterialMetadata(UUID.fromString("c649a59c-74c5-432e-8671-f2facdf47d30"))

        // then
        assertEquals(116448, material.size)
    }

    @Test
    fun `it should throw an exception if ad material metadata is not present`() {
        // given
        val storage = LocalMetadataStorage()
        val invalidUuid = UUID.fromString("18dfab4b-f2e0-44a4-8c81-6718440634d3")

        // when
        val exception = assertThrows<IllegalArgumentException> { storage.fetchMaterialMetadata(invalidUuid) }

        // then
        assertEquals("Could not find file $invalidUuid in $storageDir", exception.message)
    }
}
