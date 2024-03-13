package bg.tuvarna.sit.web;

import bg.tuvarna.sit.context.ApplicationContext;
import bg.tuvarna.sit.models.dto.TournamentResponseGetAllPlayersDto;
import bg.tuvarna.sit.service.TournamentService;
import static bg.tuvarna.sit.utils.ServletUtils.marshalToXml;
import static bg.tuvarna.sit.utils.ServletUtils.sendErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.xml.bind.JAXBException;

@WebServlet(urlPatterns = {"/players", "/players/"})
public class ShowAllPlayersServlet extends HttpServlet {

  private final TournamentService service = ApplicationContext.getTournamentService();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    resp.setContentType("application/xml;charset=UTF-8");

    try {
      TournamentResponseGetAllPlayersDto all = service.getAll();
      marshalToXml(all, TournamentResponseGetAllPlayersDto.class, resp.getOutputStream());
      resp.setStatus(HttpServletResponse.SC_OK);
    } catch (JAXBException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "Error processing XML.");
    } catch (Exception e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "An internal error occurred. Please try again later.");
    }
  }
}
