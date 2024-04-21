package com.neperix.advalidation.notification

import java.util.concurrent.ConcurrentLinkedQueue

class InMemoryChannel : NotificationChannel {

    private val queue = ConcurrentLinkedQueue<ValidationResult>()

    override fun send(result: ValidationResult) {
        queue.offer(result)
    }

    fun getFirst(): ValidationResult? {
        return queue.poll()
    }
}
