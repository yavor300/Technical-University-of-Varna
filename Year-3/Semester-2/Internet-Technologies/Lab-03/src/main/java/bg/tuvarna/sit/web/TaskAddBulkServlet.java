package bg.tuvarna.sit.web;

import bg.tuvarna.sit.context.ApplicationContext;
import bg.tuvarna.sit.exceptions.TaskNotAddedException;
import bg.tuvarna.sit.exceptions.TaskValidationException;
import bg.tuvarna.sit.models.dto.TaskRequestAddBulkDto;
import bg.tuvarna.sit.models.dto.TaskResponseBulkDto;
import bg.tuvarna.sit.service.TaskService;
import static bg.tuvarna.sit.utils.ServletUtils.marshalToXml;
import static bg.tuvarna.sit.utils.ServletUtils.parseRequest;
import static bg.tuvarna.sit.utils.ServletUtils.sendErrorResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.xml.bind.JAXBException;


@WebServlet(urlPatterns = "/add/bulk")
public class TaskAddBulkServlet extends HttpServlet {

  private final TaskService taskService = ApplicationContext.getTaskService();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

    resp.setContentType("application/xml;charset=UTF-8");

    try {
      TaskRequestAddBulkDto taskRequestAddBulkDto = parseRequest(req, TaskRequestAddBulkDto.class);
      TaskResponseBulkDto responseBulkDto = taskService.addBulk(taskRequestAddBulkDto);
      sendSuccessResponse(resp, responseBulkDto);
    } catch (TaskValidationException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
              "Failed to add task: " + e.getMessage());
    } catch (TaskNotAddedException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "Failed to add task at bulk: " + e.getMessage());
    } catch (JAXBException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "Error processing XML.");
    } catch (Exception e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "An internal error occurred. Please try again later.");
    }
  }

  private void sendSuccessResponse(HttpServletResponse resp, TaskResponseBulkDto taskResponseBulkDto)
          throws IOException, JAXBException {

    resp.setStatus(HttpServletResponse.SC_CREATED);
    marshalToXml(taskResponseBulkDto, TaskResponseBulkDto.class, resp.getOutputStream());
  }
}

