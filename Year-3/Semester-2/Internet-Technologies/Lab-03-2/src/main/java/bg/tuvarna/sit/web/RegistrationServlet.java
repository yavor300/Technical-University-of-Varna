package bg.tuvarna.sit.web;

import bg.tuvarna.sit.context.ApplicationContext;
import bg.tuvarna.sit.models.dto.TournamentRequestAddPlayerDto;
import bg.tuvarna.sit.models.dto.TournamentResponsePlayerDto;
import bg.tuvarna.sit.service.TournamentService;
import static bg.tuvarna.sit.utils.ServletUtils.marshalToXml;
import static bg.tuvarna.sit.utils.ServletUtils.parseRequest;
import static bg.tuvarna.sit.utils.ServletUtils.sendErrorResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.xml.bind.JAXBException;

@WebServlet(urlPatterns = "/players/add")
public class RegistrationServlet extends HttpServlet {

  private final TournamentService service = ApplicationContext.getTournamentService();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

    resp.setContentType("application/xml;charset=UTF-8");

    try {

      TournamentRequestAddPlayerDto requestAddPlayerDto = parseRequest(req, TournamentRequestAddPlayerDto.class);
      TournamentResponsePlayerDto responseAddPlayerDto = service.add(requestAddPlayerDto);
      sendSuccessResponse(resp, responseAddPlayerDto);
    } catch (JAXBException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "Error processing XML.");
    } catch (Exception e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "An internal error occurred. Please try again later.");
    }
  }

  private void sendSuccessResponse(HttpServletResponse resp, TournamentResponsePlayerDto dto)
          throws IOException, JAXBException {

    resp.setStatus(HttpServletResponse.SC_CREATED);
    marshalToXml(dto, TournamentResponsePlayerDto.class, resp.getOutputStream());
  }
}
