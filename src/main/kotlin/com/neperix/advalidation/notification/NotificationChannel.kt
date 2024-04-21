package com.neperix.advalidation.notification

interface NotificationChannel {

    fun send(result: ValidationResult)
}
