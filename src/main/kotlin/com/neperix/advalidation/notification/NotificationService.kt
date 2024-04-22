package com.neperix.advalidation.notification

interface NotificationService {

    fun send(result: ValidationResult)
}
