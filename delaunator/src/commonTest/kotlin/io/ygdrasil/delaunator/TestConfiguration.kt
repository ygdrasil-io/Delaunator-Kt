package io.ygdrasil.delaunator

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.config.LogLevel

class TestConfiguration : AbstractProjectConfig() {

    override val autoScanEnabled = true
    override val logLevel = LogLevel.Trace

}