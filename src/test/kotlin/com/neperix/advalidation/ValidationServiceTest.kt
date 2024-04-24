package com.neperix.advalidation

import com.neperix.advalidation.metadata.AdMaterialMetadata
import com.neperix.advalidation.metadata.LocalMetadataStorage
import com.neperix.advalidation.metadata.MetadataService
import com.neperix.advalidation.notification.NotificationService
import com.neperix.advalidation.rules.FileSizeRule
import com.neperix.advalidation.rules.ResolutionRule
import com.neperix.advalidation.rules.RulesService
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

internal class ValidationServiceTest {

    private val storage = LocalMetadataStorage()

    @Test
    fun `it should mark all successes if material is valid`() {
        // given
        val metadataService = mockk<MetadataService>()
        val rulesService = mockk<RulesService>()
        val notificationService = mockk<NotificationService>()
        val service = ValidationService(metadataService, rulesService, notificationService)

        val command = ValidationCommand(UUID.randomUUID(), UUID.randomUUID())

        every { metadataService.fetchMaterialMetadata(command.materialUUID) } returns
            AdMaterialMetadata("jpeg", 120000, 120, 800, 600, 0)

        every { rulesService.getRules(command.productUUID) } returns listOf(
            FileSizeRule(150000),
            ResolutionRule(heightRange = 600..800, widthRange = 800..1024, 100..300)
        )

        val result = slot<ValidationResult>()
        every { notificationService.send(result = capture(result)) } answers { result.captured }

        // when
        service.process(command)

        // then
        assertEquals(2, result.captured.successes.size)
        assertEquals(0, result.captured.failures.size)
    }

    @Test
    fun `it should mark only failures for rules that do not pass`() {
        // given
        val metadataService = mockk<MetadataService>()
        val rulesService = mockk<RulesService>()
        val notificationService = mockk<NotificationService>()
        val service = ValidationService(metadataService, rulesService, notificationService)

        val command = ValidationCommand(UUID.randomUUID(), UUID.randomUUID())

        every { metadataService.fetchMaterialMetadata(command.materialUUID) } returns
            AdMaterialMetadata("jpeg", 200000, 120, 800, 600, 0)

        every { rulesService.getRules(command.productUUID) } returns listOf(
            FileSizeRule(150000),
            ResolutionRule(heightRange = 600..800, widthRange = 800..1024, 100..300)
        )

        val result = slot<ValidationResult>()
        every { notificationService.send(result = capture(result)) } answers { result.captured }

        // when
        service.process(command)

        // then
        assertEquals(1, result.captured.successes.size)
        assertEquals(1, result.captured.failures.size)
        assertEquals("Found size (200000); Expected within range 0..150000)", result.captured.failures[0])
    }
}
