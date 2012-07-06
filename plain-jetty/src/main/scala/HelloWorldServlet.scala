package jetty.perftest

import javax.servlet._
import javax.servlet.http._

class HelloWorldServlet extends HttpServlet {
  override def service(req: HttpServletRequest, res: HttpServletResponse) {

    res.getWriter.println("Hello, world!")
    res.getWriter.close()
  }
}