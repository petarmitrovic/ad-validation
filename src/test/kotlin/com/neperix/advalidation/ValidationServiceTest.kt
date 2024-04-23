package com.neperix.advalidation

import com.neperix.advalidation.metadata.LocalMetadataStorage
import com.neperix.advalidation.notification.InMemoryNotificationServic
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import java.util.UUID

internal class ValidationServiceTest {

    private val storage = LocalMetadataStorage()

    @Test
    fun `it should return success if material is valid`() {
        // given
        val channel = InMemoryNotificationServic()
        val service = ValidationService(storage, channel)
        val materialUuid = UUID.fromString("c649a59c-74c5-432e-8671-f2facdf47d30")

        // when
        service.process(materialUuid)

        // then
        val result = channel.getFirst() ?: fail("Didn't receive the validation result")
        assertTrue(result.success)
        assertNull(result.error)
    }

    @Test
    fun `it should return error if material is not valid`() {
        // given
        val channel = InMemoryNotificationServic()
        val service = ValidationService(storage, channel)
        val materialId = UUID.fromString("2fd04780-40e0-417c-ad1b-b4beb72841ae")

        // when
        service.process(materialId)

        // then
        val result = channel.getFirst() ?: fail("Didn't receive the validation result")
        assertFalse(result.success)
        assertEquals("Material should be larger than 100 kB", result.error)
    }
}
