package com.neperix.advalidation.materials

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

internal class LocalMaterialsStorageTest {

    private val storageDir = this.javaClass.getResource("/materials")!!.path

    @Test
    fun `it should fetch ad material if present`() {
        // given
        val storage = LocalMaterialsStorage(storageDir)

        // when
        val material = storage.fetchMaterial(UUID.fromString("c649a59c-74c5-432e-8671-f2facdf47d30"))

        // then
        assertEquals(116448, material.size)
    }

    @Test
    fun `it should throw an exception if ad material is not present`() {
        // given
        val storage = LocalMaterialsStorage(storageDir)
        val invalidUuid = UUID.fromString("18dfab4b-f2e0-44a4-8c81-6718440634d3")

        // when
        val exception = assertThrows<IllegalArgumentException> { storage.fetchMaterial(invalidUuid) }

        // then
        assertEquals("Could not find file $invalidUuid in $storageDir", exception.message)
    }
}