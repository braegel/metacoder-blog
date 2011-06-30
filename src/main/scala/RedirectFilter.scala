package de.metacoder.blog

import javax.servlet._
import http.HttpServletResponse
import util.Logging

/**
 * Created by IntelliJ IDEA.
 * Author: fbe und SuperTux88
 * Date: 13.06.11
 * Time: 23:39
 */

class RedirectFilter extends Filter with Logging{

  def init(filterconfig : FilterConfig) {}

  def doFilter(request : ServletRequest, response : ServletResponse, chain : FilterChain) = response.asInstanceOf[HttpServletResponse].sendRedirect("/blog.highspeed")

  def destroy() {}
}