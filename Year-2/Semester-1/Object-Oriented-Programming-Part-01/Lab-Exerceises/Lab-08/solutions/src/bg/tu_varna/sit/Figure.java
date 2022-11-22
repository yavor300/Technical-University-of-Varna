package bg.tu_varna.sit;

public interface Figure {

  String SIDE_ERROR_MESSAGE = "Figure side must be a positive number!";
  String HEIGHT_ERROR_MESSAGE = "Height must be a positive number!";
  String INVALID_TRIANGLE_SIDES = "Invalid triangle sides!";

  double area();

  double perimeter();
}
