package bg.tuvarna.sit.web;

import bg.tuvarna.sit.context.ApplicationContext;
import bg.tuvarna.sit.exceptions.AnimalNotFoundException;
import bg.tuvarna.sit.exceptions.AnimalValidationException;
import bg.tuvarna.sit.exceptions.SchemaNotFoundException;
import bg.tuvarna.sit.models.dto.AnimalRequestPutDto;
import bg.tuvarna.sit.models.dto.AnimalResponseReadDto;
import bg.tuvarna.sit.service.AnimalService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;

import static bg.tuvarna.sit.utils.ServletUtils.inputStreamToByteArray;
import static bg.tuvarna.sit.utils.ServletUtils.marshalToXml;
import static bg.tuvarna.sit.utils.ServletUtils.parseRequest;
import static bg.tuvarna.sit.utils.ServletUtils.sendErrorResponse;
import static bg.tuvarna.sit.utils.ServletUtils.validateXMLAgainstSchema;

@WebServlet(urlPatterns = "/edit")
public class AnimalEditServlet extends HttpServlet {

  private final AnimalService service = ApplicationContext.getAnimalService();

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) {


    resp.setContentType("application/xml;charset=UTF-8");

    try {

      byte[] xmlBytes = inputStreamToByteArray(req.getInputStream());
      validateXMLAgainstSchema(xmlBytes, "animalRequestPut.xsd");
      AnimalRequestPutDto dto = parseRequest(new ByteArrayInputStream(xmlBytes), AnimalRequestPutDto.class);
      AnimalResponseReadDto updated = service.put(dto);
      marshalToXml(updated, AnimalResponseReadDto.class, resp.getOutputStream());
      resp.setStatus(HttpServletResponse.SC_OK);
    } catch (AnimalNotFoundException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
    } catch (AnimalValidationException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    } catch (JAXBException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing XML.");
    } catch (SAXException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
              "Validation failed: " + e.getMessage());
    } catch (SchemaNotFoundException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "Failed to load XSD schema: " + e.getMessage());
    } catch (Exception e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "An unexpected error occurred. Please try again later.");
    }
  }
}
