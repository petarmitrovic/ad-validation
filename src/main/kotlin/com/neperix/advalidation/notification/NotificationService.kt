package com.neperix.advalidation.notification

import com.neperix.advalidation.ValidationResult

interface NotificationService {

    fun send(result: ValidationResult)
}
