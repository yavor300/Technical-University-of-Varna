package bg.tuvarna.sit.utils;

import bg.tuvarna.sit.models.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class ServletUtils {


  public static <T> T parseRequest(HttpServletRequest req, Class<T> dtoClass) throws JAXBException, IOException {

    JAXBContext jaxbContext = JAXBContext.newInstance(dtoClass);
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

    return dtoClass.cast(unmarshaller.unmarshal(req.getInputStream()));
  }

  public static void marshalToXml(Object responseDto, Class<?> dtoClass, OutputStream outputStream)
          throws JAXBException {

    JAXBContext jaxbContext = JAXBContext.newInstance(dtoClass);
    Marshaller marshaller = jaxbContext.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    marshaller.marshal(responseDto, outputStream);
  }

  public static void sendErrorResponse(HttpServletResponse resp, int statusCode, String errorMessage) {

    try {
      ErrorResponse errorResponse = new ErrorResponse(errorMessage);
      resp.setStatus(statusCode);
      marshalToXml(errorResponse, ErrorResponse.class, resp.getOutputStream());
    } catch (JAXBException | IOException e) {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.setContentType("text/plain");
      try {
        resp.getWriter().write("An internal error occurred. Please try again later.");
      } catch (IOException ex) {
        // TODO: In real project a logger should be used to log that type of error
        System.err.println("An exception occurred while handling another exception: " + ex.getMessage());
      }
    }
  }

}
