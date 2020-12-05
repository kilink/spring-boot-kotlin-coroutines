package net.kilink

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.binder.MeterBinder
import kotlinx.coroutines.debug.DebugProbes
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(
    value = [
        MetricsAutoConfiguration::class,
        CompositeMeterRegistryAutoConfiguration::class
    ]
)
@ConditionalOnClass(MeterRegistry::class)
@ConditionalOnBean(MeterRegistry::class)
public class KotlinCoroutinesAutoConfiguration {
    @Bean
    public fun coroutineMetrics(probes: Probes): MeterBinder {
        return KotlinCoroutinesMetrics(probes)
    }

    @Bean
    public fun probes(): Probes {
        return object : Probes {
            override fun enable() = DebugProbes.install()
            override fun disable() = DebugProbes.uninstall()
            override fun isInstalled() = DebugProbes.isInstalled
            override fun dumpCoroutines() = if (isInstalled()) {
                DebugProbes.dumpCoroutinesInfo()
            } else {
                emptyList()
            }
        }
    }
}
