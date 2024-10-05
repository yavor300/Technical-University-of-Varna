package bg.tuvarna.sit.web;

import bg.tuvarna.sit.context.ApplicationContext;
import bg.tuvarna.sit.exceptions.AnimalNotAddedException;
import bg.tuvarna.sit.exceptions.AnimalValidationException;
import bg.tuvarna.sit.exceptions.SchemaNotFoundException;
import bg.tuvarna.sit.models.dto.AnimalRequestAddDto;
import bg.tuvarna.sit.models.dto.AnimalResponseReadDto;
import bg.tuvarna.sit.service.AnimalService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static bg.tuvarna.sit.utils.ServletUtils.*;
import static bg.tuvarna.sit.utils.ServletUtils.validateXMLAgainstSchema;

@WebServlet(urlPatterns = "/add")
public class AnimalAddServlet extends HttpServlet {

  private final AnimalService service = ApplicationContext.getAnimalService();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

    resp.setContentType("application/xml;charset=UTF-8");

    try {

      byte[] xmlBytes = inputStreamToByteArray(req.getInputStream());
      validateXMLAgainstSchema(xmlBytes, "animalRequestAdd.xsd");
      AnimalRequestAddDto animalRequestAddDto = parseRequest(new ByteArrayInputStream(xmlBytes), AnimalRequestAddDto.class);
      AnimalResponseReadDto animalResponseReadDto = service.add(animalRequestAddDto);
      sendSuccessResponse(resp, animalResponseReadDto);
    } catch (AnimalValidationException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
              "Failed to add animal: " + e.getMessage());
    } catch (AnimalNotAddedException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "Failed to add animal: " + e.getMessage());
    } catch (JAXBException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "Error processing XML.");
    } catch (SAXException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
              "Validation failed: " + e.getMessage());
    } catch (SchemaNotFoundException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "Failed to load XSD schema: " + e.getMessage());
    } catch (Exception e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "An internal error occurred. Please try again later.");
    }
  }

  private void sendSuccessResponse(HttpServletResponse resp, AnimalResponseReadDto animalResponseReadDto)
          throws JAXBException, IOException {

    resp.setStatus(HttpServletResponse.SC_CREATED);
    marshalToXml(animalResponseReadDto, AnimalResponseReadDto.class, resp.getOutputStream());
  }
}
