package bg.tuvarna.sit.web;

import bg.tuvarna.sit.context.ApplicationContext;
import bg.tuvarna.sit.exceptions.TaskNotFoundException;
import bg.tuvarna.sit.exceptions.TaskValidationException;
import bg.tuvarna.sit.models.dto.TaskRequestPutDto;
import bg.tuvarna.sit.models.dto.TaskResponseDto;
import bg.tuvarna.sit.service.TaskService;
import static bg.tuvarna.sit.utils.ServletUtils.marshalToXml;
import static bg.tuvarna.sit.utils.ServletUtils.parseRequest;
import static bg.tuvarna.sit.utils.ServletUtils.sendErrorResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

@WebServlet(urlPatterns = "/edit")
public class TaskEditServlet extends HttpServlet {

  private final TaskService taskService = ApplicationContext.getTaskService();

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

    resp.setContentType("application/xml;charset=UTF-8");

    try {
      TaskRequestPutDto dto = parseRequest(req, TaskRequestPutDto.class);
      TaskResponseDto updatedTask = taskService.put(dto);
      marshalToXml(updatedTask, TaskResponseDto.class, resp.getOutputStream());
      resp.setStatus(HttpServletResponse.SC_OK);
    } catch (TaskNotFoundException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
    } catch (TaskValidationException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    } catch (JAXBException e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing XML.");
    } catch (Exception e) {
      sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
              "An unexpected error occurred. Please try again later.");
    }
  }
}
