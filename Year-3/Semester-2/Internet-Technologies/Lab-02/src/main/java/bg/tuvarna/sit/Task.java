package bg.tuvarna.sit;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = "/index.jsp")
public class Task extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    String a = req.getParameter("a");
    String b = req.getParameter("b");

    String response;
    if (a == null || b == null) {
      resp.setStatus(400);
      response = "Missing mandatory query parameters.";
    } else {
      response = String.valueOf(Integer.valueOf(a) + Integer.valueOf(b));
    }

    resp.setContentType("text/xml");
    PrintWriter writer = resp.getWriter();
    writer.println("<messages>");
    writer.println("<message>");
    writer.println(response);
    writer.println("</message>");
    writer.println("</messages>");
  }
}

