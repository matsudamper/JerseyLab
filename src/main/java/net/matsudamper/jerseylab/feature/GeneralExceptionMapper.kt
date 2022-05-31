package net.matsudamper.jerseylab.feature

import java.io.PrintWriter
import java.io.StringWriter
import javax.inject.Inject
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo
import javax.ws.rs.ext.ExceptionMapper

class GeneralExceptionMapper @Inject constructor(
    @Context
    private val uriInfo: UriInfo,
    @Context
    private val servletRequest: HttpServletRequest,
) : ExceptionMapper<Exception> {

    override fun toResponse(exception: Exception?): Response {
        return Response.ok(
            "error:\n" +
                StringWriter().use {
                    PrintWriter(it).use {
                        exception!!.printStackTrace(it)
                    }
                    it.toString()
                }
        ).build()
    }
}