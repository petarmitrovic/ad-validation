package com.neperix.advalidation.notification

import java.util.concurrent.ConcurrentLinkedQueue

class InMemoryNotificationServic : NotificationService {

    private val queue = ConcurrentLinkedQueue<ValidationResult>()

    override fun send(result: ValidationResult) {
        queue.offer(result)
    }

    fun getFirst(): ValidationResult? {
        return queue.poll()
    }
}
