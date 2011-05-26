package de.metacoder.blog

import javax.servlet.http.{HttpServlet,
                           HttpServletRequest => Request,
                           HttpServletResponse => Response}

class Hello  extends HttpServlet {
  override def doGet(request : Request, response : Response) {
    response.getWriter.print("Hello World")
  }
}