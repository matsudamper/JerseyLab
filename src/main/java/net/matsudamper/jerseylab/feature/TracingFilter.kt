package net.matsudamper.jerseylab.feature

import javax.annotation.Priority
import javax.inject.Inject
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Priorities
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.core.Context
import javax.ws.rs.core.UriInfo

@Priority(Priorities.ENTITY_CODER)
class TracingFilter @Inject constructor(
    @Context
    private val uriInfo: UriInfo,
    @Context
    private val servletRequest: HttpServletRequest,
) : ContainerRequestFilter {

    override fun filter(requestContext: ContainerRequestContext?) {
        val pathParams: List<Pair<String, String>> = this.uriInfo.pathParameters.toList().map { pair ->
            pair.second.map { pair.first to it }
        }.flatten().sortedBy { it.first }

        val queryParams: List<Pair<String, String>> = this.uriInfo.queryParameters.toList().map { pair ->
            pair.second.map { pair.first to it }
        }.flatten().sortedBy { it.first }

        println(
            mapOf(
                "UserAgent" to servletRequest.getHeader("User-Agent").orEmpty(),
                "pathParam" to pathParams.joinToString(" ") { "${it.first}=${it.second};" },
                "queryParam" to queryParams.joinToString(" ") { "${it.first}=${it.second};" },
            )
        )
    }
}
