package bg.tu_varna.sit.task1;

import java.util.ArrayList;
import java.util.List;

public class Hotel {

  private final List<Room> rooms;

  public Hotel() {
    this.rooms = new ArrayList<>();
  }

  public boolean addRoom(Room room) {

    return rooms.add(room);
  }

  public boolean createReservation(Exposure exposure) {

    for (Room room : rooms) {
      if (room.getExposure() == exposure) {
        room.setAvailable(false);
        return true;
      }
    }

    return false;
  }

  public double calculateBookedRoomsDiscount() {

    double result = 0;

    for (Room room : rooms) {
      if (!room.isAvailable()) {
        result += room.discount();
      }
    }

    return result;
  }

  public double calculateAveragePriceOfAvailableRoomsWithSeaView() {

    double result = 0;
    int count = 0;

    for (Room room : rooms) {
      if (room.isAvailable() && room.getExposure() == Exposure.SEA_VIEW) {
        result += room.getPricePerDay();
        count++;
      }
    }

    if (count == 0) {
      return 0;
    }

    return result / count;
  }

  @Override
  public String toString() {

    StringBuilder result = new StringBuilder();

    for (Room room : rooms) {
      result.append(room).append("\n");
    }

    return result.toString();
  }
}
