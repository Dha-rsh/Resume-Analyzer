package resumeanalyzer.controller;
import jakarta.servlet.http.*;
import resumeanalyzer.service.ResumeService;

import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;


@MultipartConfig
public class AnalyzeServlet extends HttpServlet{

    protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
{
   res.getWriter().write("GET not supported");
}
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    res.setContentType("application/json");
    try {
        Part filePart = req.getPart("file");
        String jobDesc = req.getParameter("jobDesc");

        if (filePart == null) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write("{\"error\":\"No file uploaded\"}");
            return;
        }

        InputStream fileContent = filePart.getInputStream();
        String fileName = filePart.getSubmittedFileName();
        String result = new ResumeService().analyze(fileContent, fileName, jobDesc);

        res.getWriter().write(result);

    } catch (Exception e) {
        res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        res.getWriter().write("{\"error\":\"" + e.getMessage().replace("\"","\\\"") + "\"}");
        e.printStackTrace();
    }
}
}