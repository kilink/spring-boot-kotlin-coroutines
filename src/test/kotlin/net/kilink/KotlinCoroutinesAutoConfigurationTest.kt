package net.kilink

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.test.context.runner.ApplicationContextRunner

class KotlinCoroutinesAutoConfigurationTest {
    private val applicationContext = ApplicationContextRunner()
        .withConfiguration(
            AutoConfigurations.of(
                MetricsAutoConfiguration::class.java,
                CompositeMeterRegistryAutoConfiguration::class.java,
                KotlinCoroutinesAutoConfiguration::class.java
            )
        )

    @Test
    fun testAutoConfiguration() {
        applicationContext.run { context ->
            assertThat(context).hasSingleBean(KotlinCoroutinesMetrics::class.java)
        }
    }
}
