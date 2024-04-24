package com.neperix.advalidation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/validations")
class ValidationController(
    private val validationService: ValidationService
) {

    @PostMapping
    fun validate(@RequestBody command: ValidationCommand): ResponseEntity<String> {
        validationService.process(command)

        return ResponseEntity.ok("OK")
    }
}
