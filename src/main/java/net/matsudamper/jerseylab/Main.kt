package net.matsudamper.jerseylab

import org.eclipse.jetty.server.*
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.glassfish.jersey.servlet.ServletContainer
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener


class MyContextListener : ServletContextListener {
    override fun contextInitialized(sce: ServletContextEvent?) {
        println("contextInitialized")
    }

    override fun contextDestroyed(sce: ServletContextEvent?) {
        println("contextDestroyed")
    }
}

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val server = Server().also { server ->
                val httpConnector = ServerConnector(
                    server,
                    HttpConnectionFactory(
                        HttpConfiguration().also { config ->
                            config.sendServerVersion = false
                        }
                    )
                ).also { connector ->
                    connector.port = 80
                }

                server.connectors = listOf<Connector>(
                    httpConnector
                ).toTypedArray()

                server.requestLog = AsyncNCSARequestLog().also { log ->
                    log.filename = "logs/yyyy_mm_dd.access.log"
                    log.filenameDateFormat = "yyyyMMdd"
                    log.retainDays = 7
                    log.isAppend = true
                    log.isExtended = true
                    log.logCookies = true
                    log.logTimeZone = "GMT"
                }

                val resourceConfig = JerseyConfig()
                val container = ServletContainer(resourceConfig)
                val holder = ServletHolder(container).also { holder ->
                    holder.initParameters.toMap().map {
                        println(it)
                    }
                }

                ServletContextHandler(server, "/").also { context ->
                    context.addServlet(holder, "/*")
                    context.addEventListener(MyContextListener())
                }
            }

            server.start()
            server.join()
        }
    }
}
