package net.kilink

import com.google.common.base.Suppliers
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.binder.MeterBinder
import kotlinx.coroutines.debug.State
import java.util.concurrent.TimeUnit

public class KotlinCoroutinesMetrics(private val probes: Probes) : MeterBinder {
    override fun bindTo(registry: MeterRegistry) {
        for (state in State.values()) {
            registry.gauge(
                "coroutine.count",
                listOf(Tag.of("state", state.name)),
                state,
                this::coroutineCount
            )
        }
    }

    private val coroutineInfo = Suppliers.memoizeWithExpiration(
        probes::dumpCoroutines,
        10,
        TimeUnit.SECONDS
    )

    private fun coroutineCount(state: State): Double {
        return coroutineInfo.get()
            .count { coroutineInfo -> coroutineInfo.state == state }
            .toDouble()
    }
}
