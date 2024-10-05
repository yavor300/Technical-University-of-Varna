package bg.tuvarna.sit.web;

import bg.tuvarna.sit.context.ApplicationContext;
import bg.tuvarna.sit.exceptions.AnimalValidationException;
import bg.tuvarna.sit.models.dto.AnimalResponseGetAllDto;
import bg.tuvarna.sit.service.AnimalService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.xml.bind.JAXBException;

import static bg.tuvarna.sit.utils.ServletUtils.marshalToXml;
import static bg.tuvarna.sit.utils.ServletUtils.sendErrorResponse;

@WebServlet(urlPatterns = "/view/*")
public class AnimalGetTypeServlet extends HttpServlet {

  private final AnimalService service = ApplicationContext.getAnimalService();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

    resp.setContentType("application/xml;charset=UTF-8");

    try {

      String pathInfo = req.getPathInfo();

      if (pathInfo == null || pathInfo.equals("/")) {
        sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                "Missing animal type.");
      } else {
        String type = pathInfo.substring(1);
        AnimalResponseGetAllDto animalResponseGetAllDto = service.getAllByType(type);
        marshalToXml(animalResponseGetAllDto, AnimalResponseGetAllDto.class, resp.getOutputStream());
        resp.setStatus(HttpServletResponse.SC_OK);
      }
    } catch (AnimalValidationException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    } catch (JAXBException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "Error processing XML.");
    } catch (Exception e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "An internal error occurred. Please try again later.");
    }
  }
}
