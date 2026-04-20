package resumeanalyzer.controller;
import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.*;
public class CORSFilter implements Filter
{
  public void doFilter(ServletRequest req,ServletResponse res,FilterChain chain) throws IOException,ServletException
  {
    HttpServletResponse resp=(HttpServletResponse ) res;
    resp.setHeader("Access-Control-Allow-Origin","http://localhost:5173");
    resp.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS");
    resp.setHeader("Access-Control-Allow-Headers","Content-Type");
    chain.doFilter(req,res);
  }
}