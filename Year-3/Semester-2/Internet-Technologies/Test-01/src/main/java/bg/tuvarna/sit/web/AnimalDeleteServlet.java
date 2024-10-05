package bg.tuvarna.sit.web;

import bg.tuvarna.sit.context.ApplicationContext;
import bg.tuvarna.sit.exceptions.AnimalNotFoundException;
import bg.tuvarna.sit.exceptions.AnimalValidationException;
import bg.tuvarna.sit.models.dto.AnimalResponseReadDto;
import bg.tuvarna.sit.service.AnimalService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.xml.bind.JAXBException;

import static bg.tuvarna.sit.utils.ServletUtils.marshalToXml;
import static bg.tuvarna.sit.utils.ServletUtils.sendErrorResponse;

@WebServlet(urlPatterns = "/delete/*")
public class AnimalDeleteServlet extends HttpServlet {

  private final AnimalService service = ApplicationContext.getAnimalService();

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {

    resp.setContentType("application/xml;charset=UTF-8");

    try {

      String pathInfo = req.getPathInfo();
      if (pathInfo == null || pathInfo.equals("/")) {
        sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing ID parameter.");
      } else {
        String id = pathInfo.substring(1);
        AnimalResponseReadDto animalResponseReadDto = service.delete(id);
        marshalToXml(animalResponseReadDto, AnimalResponseReadDto.class, resp.getOutputStream());
        resp.setStatus(HttpServletResponse.SC_OK);
      }
    } catch (AnimalValidationException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    } catch (AnimalNotFoundException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
    } catch (JAXBException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "Error processing XML.");
    } catch (Exception e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "An internal error occurred. Please try again later.");
    }
  }
}
