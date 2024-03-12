package bg.tuvarna.sit.web;

import bg.tuvarna.sit.context.ApplicationContext;
import bg.tuvarna.sit.exceptions.SchemaNotFoundException;
import bg.tuvarna.sit.exceptions.TaskNotAddedException;
import bg.tuvarna.sit.exceptions.TaskValidationException;
import bg.tuvarna.sit.models.dto.TaskRequestAddDto;
import bg.tuvarna.sit.models.dto.TaskResponseDto;
import bg.tuvarna.sit.service.TaskService;
import static bg.tuvarna.sit.utils.ServletUtils.inputStreamToByteArray;
import static bg.tuvarna.sit.utils.ServletUtils.marshalToXml;
import static bg.tuvarna.sit.utils.ServletUtils.parseRequest;
import static bg.tuvarna.sit.utils.ServletUtils.sendErrorResponse;
import static bg.tuvarna.sit.utils.ServletUtils.validateXMLAgainstSchema;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

@WebServlet(urlPatterns = "/add")
public class TaskAddServlet extends HttpServlet {

  private final TaskService taskService = ApplicationContext.getTaskService();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

    resp.setContentType("application/xml;charset=UTF-8");

    try {

      byte[] xmlBytes = inputStreamToByteArray(req.getInputStream());
      validateXMLAgainstSchema(xmlBytes, "taskRequestAddDto.xsd");
      TaskRequestAddDto taskRequestAddDto = parseRequest(new ByteArrayInputStream(xmlBytes), TaskRequestAddDto.class);
      TaskResponseDto taskResponseDto = taskService.add(taskRequestAddDto);
      sendSuccessResponse(resp, taskResponseDto);
    } catch (SAXException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
              "Validation failed: " + e.getMessage());
    } catch (TaskValidationException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
              "Failed to add task: " + e.getMessage());
    } catch (SchemaNotFoundException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "Failed to load XSD schema: " + e.getMessage());
    } catch (TaskNotAddedException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "Failed to add task: " + e.getMessage());
    } catch (JAXBException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "Error processing XML.");
    } catch (Exception e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "An internal error occurred. Please try again later.");
    }
  }

  private void sendSuccessResponse(HttpServletResponse resp, TaskResponseDto taskResponseDto)
          throws JAXBException, IOException {

    resp.setStatus(HttpServletResponse.SC_CREATED);
    marshalToXml(taskResponseDto, TaskResponseDto.class, resp.getOutputStream());
  }
}

