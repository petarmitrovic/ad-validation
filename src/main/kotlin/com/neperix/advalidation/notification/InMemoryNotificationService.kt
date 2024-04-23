package com.neperix.advalidation.notification

import com.neperix.advalidation.ValidationResult
import java.util.concurrent.ConcurrentLinkedQueue

class InMemoryNotificationService : NotificationService {

    private val queue = ConcurrentLinkedQueue<ValidationResult>()

    override fun send(result: ValidationResult) {
        queue.offer(result)
    }

    fun getFirst(): ValidationResult? {
        return queue.poll()
    }
}
