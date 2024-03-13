package bg.tuvarna.sit.web;

import bg.tuvarna.sit.context.ApplicationContext;
import bg.tuvarna.sit.exceptions.PlayerNotFoundException;
import bg.tuvarna.sit.exceptions.PlayerValidationException;
import bg.tuvarna.sit.models.dto.TournamentResponsePlayerDto;
import bg.tuvarna.sit.service.TournamentService;
import static bg.tuvarna.sit.utils.ServletUtils.marshalToXml;
import static bg.tuvarna.sit.utils.ServletUtils.sendErrorResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

@WebServlet(urlPatterns = "/players/view/*")
public class ShowPlayerServlet extends HttpServlet {

  private final TournamentService service = ApplicationContext.getTournamentService();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {


    resp.setContentType("application/xml;charset=UTF-8");

    try {

      String pathInfo = req.getPathInfo();

      if (pathInfo == null || pathInfo.equals("/")) {
        sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                "Missing player id.");
      } else {
        String id = pathInfo.substring(1);
        TournamentResponsePlayerDto dto = service.getById(id);
        marshalToXml(dto, TournamentResponsePlayerDto.class, resp.getOutputStream());
        resp.setStatus(HttpServletResponse.SC_OK);
      }
    } catch (JAXBException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "Error processing XML.");
    } catch (PlayerValidationException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
              e.getMessage());
    } catch (PlayerNotFoundException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND,
              e.getMessage());
    } catch (Exception e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "An internal error occurred. Please try again later.");
    }
  }
}
