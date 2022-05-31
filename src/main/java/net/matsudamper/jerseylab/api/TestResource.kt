package net.matsudamper.jerseylab.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider
import java.io.InputStream
import java.io.OutputStream
import java.lang.reflect.Type
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.Provider

@Path("/")
class TestResource @Inject constructor(
) {

    @Produces(MediaType.TEXT_PLAIN)
    @Path("")
    @GET
    fun hello(): String {
        return "Hello World!"
    }

    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    fun query(request: JsonRequest): String {
        return request.toString()
    }
}

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class JacksonJsonProviderImp : JacksonJsonProvider(
    mapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS
) {
    override fun readFrom(
        type: Class<Any>?,
        genericType: Type?,
        annotations: Array<out Annotation>?,
        mediaType: MediaType?,
        httpHeaders: MultivaluedMap<String, String>?,
        entityStream: InputStream?
    ): Any {
        println("readFrom")
        println("mediaType: $mediaType")
//        if (mediaType == MediaType.APPLICATION_JSON_TYPE) {
//            return mapper.readValue(entityStream, type)
//        }

        return super.readFrom(type, genericType, annotations, mediaType, httpHeaders, entityStream)
    }

    override fun writeTo(
        value: Any?,
        type: Class<*>?,
        genericType: Type?,
        annotations: Array<out Annotation>?,
        mediaType: MediaType?,
        httpHeaders: MultivaluedMap<String, Any>?,
        entityStream: OutputStream?
    ) {
        println("writeTo")
        super.writeTo(value, type, genericType, annotations, mediaType, httpHeaders, entityStream)
    }

    companion object {
        private val mapper = jacksonObjectMapper()
    }
}

data class JsonRequest(
    @JsonProperty("query") val query: String,
)
