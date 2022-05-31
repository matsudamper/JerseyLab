package net.matsudamper.jerseylab

import net.matsudamper.jerseylab.feature.TracingFilter
import javax.ws.rs.container.DynamicFeature
import javax.ws.rs.container.ResourceInfo
import javax.ws.rs.core.FeatureContext

class DynamicFeatureImpl : DynamicFeature {
    override fun configure(resourceInfo: ResourceInfo?, context: FeatureContext?) {
        context ?: return
        context.register(TracingFilter::class.java)
    }
}
