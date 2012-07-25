package org.scalatra.perftest

import org.eclipse.jetty.server.nio.SelectChannelConnector
import org.eclipse.jetty.server.{ Server }
import org.eclipse.jetty.server.handler.ContextHandlerCollection
import org.eclipse.jetty.webapp.WebAppContext
import org.eclipse.jetty.servlet.{ DefaultServlet, ServletContextHandler, ServletHolder }

object JettyMain {
  
  def main(args: Array[String]) = {
    val server: Server = new Server

    // server.setThreadPool(new org.eclipse.jetty.util.thread.QueuedThreadPool(500))
    server setGracefulShutdown 5000
    server setSendServerVersion false
    server setSendDateHeader false
    server setStopAtShutdown true

    val connector = new SelectChannelConnector
    connector setPort 8083
    connector.setReuseAddress(true)
    connector.setSoLingerTime(0)
    server addConnector connector
    
    val webapp = "src/main/webapp"
    val webApp = new WebAppContext
    webApp setContextPath "/"
    webApp setResourceBase webapp
    webApp setDescriptor (webapp+"/WEB-INF/web.xml")

    server setHandler webApp

    server.start()
  }
}