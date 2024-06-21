package dev.arbor.gtnn.api.lang

import java.util.concurrent.atomic.AtomicBoolean


/**
 * Utility to ensure that certain code is only called a single time.
 */
internal class SingleCallGuard {
    private val wasCalled = AtomicBoolean(false)

    /**
     * Checks whether the code has been called before and throws an [IllegalStateException] if so.
     * The error message will be generated using the supplied [errorProvider].
     */
    fun check(errorProvider: () -> String) {
        val hasBeenCalledBefore = wasCalled.getAndSet(true)

        if (hasBeenCalledBefore)
            error(errorProvider())
    }
}
