package net.matsudamper.jerseylab

import net.matsudamper.jerseylab.feature.GeneralExceptionMapper
import org.glassfish.jersey.server.ResourceConfig
import javax.ws.rs.ApplicationPath

@ApplicationPath("/")
open class JerseyConfig : ResourceConfig() {
    init {
        println("start JerseyConfig")

        packages("net.matsudamper.jerseylab")
        this.register(GeneralExceptionMapper::class.java)
        this.register(DynamicFeatureImpl::class.java)
    }
}

