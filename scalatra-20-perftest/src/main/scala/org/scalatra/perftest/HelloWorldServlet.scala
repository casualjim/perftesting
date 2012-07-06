package org.scalatra.perftest

import org.scalatra._
import scalate.ScalateSupport

class HelloWorldServlet extends ScalatraServlet with ScalateSupport {

  get("/") {
    "Hello, World!"
  }

  notFound {
    // Try to render a ScalateTemplate if no route matched
    findTemplate(requestPath) map { path =>
      contentType = "text/html"
      layoutTemplate(path)
    } orElse serveStaticResource() getOrElse resourceNotFound() 
  }
}
