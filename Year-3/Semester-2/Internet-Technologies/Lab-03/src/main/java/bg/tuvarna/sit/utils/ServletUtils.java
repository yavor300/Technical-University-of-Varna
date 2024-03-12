package bg.tuvarna.sit.utils;

import bg.tuvarna.sit.exceptions.SchemaNotFoundException;
import bg.tuvarna.sit.models.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ServletUtils {

  public static <T> T parseRequest(HttpServletRequest req, Class<T> dtoClass) throws Exception {

    JAXBContext jaxbContext = JAXBContext.newInstance(dtoClass);
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

    return dtoClass.cast(unmarshaller.unmarshal(req.getInputStream()));
  }

  public static <T> T parseRequest(InputStream xmlStream, Class<T> dtoClass) throws Exception {

    JAXBContext jaxbContext = JAXBContext.newInstance(dtoClass);
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

    return dtoClass.cast(unmarshaller.unmarshal(xmlStream));
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

  public static byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {

    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    inputStream.transferTo(buffer);

    return buffer.toByteArray();
  }

  public static void validateXMLAgainstSchema(byte[] xmlBytes, String xsdPath) throws Exception {

    Schema schema = getSchema(xsdPath);
    Validator validator = schema.newValidator();

    validator.validate(new SAXSource(new InputSource(new ByteArrayInputStream(xmlBytes))));
  }

  private static Schema getSchema(String xsdPath) throws SchemaNotFoundException, SAXException {

    InputStream xsdInputStream = ServletUtils.class.getClassLoader().getResourceAsStream(xsdPath);
    if (xsdInputStream == null) {
      throw new SchemaNotFoundException("Cannot find '" + xsdPath + "' in classpath.");
    }

    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

    return factory.newSchema(new StreamSource(xsdInputStream));
  }
}
