package net.kilink

import kotlinx.coroutines.debug.CoroutineInfo

public interface Probes {
    public fun enable()
    public fun disable()
    public fun isInstalled(): Boolean
    public fun dumpCoroutines(): List<CoroutineInfo>
}
