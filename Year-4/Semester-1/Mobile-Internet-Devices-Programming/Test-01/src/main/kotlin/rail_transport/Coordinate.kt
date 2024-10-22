package rail_transport

import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class Coordinate(private val longitude: Double, private val latitude: Double) {

  private val radiusOfEarth: Double = 6371.0

  fun getDistance(coordinate: Coordinate): Double {

    return (2 * asin(
      sqrt(
        sin(abs((coordinate.latitude - latitude) / 2)).pow(2) + cos(longitude) * cos(coordinate.longitude) * sin(
          abs((coordinate.longitude - longitude) / 2)
        ).pow(2)
      )
    )) * radiusOfEarth
  }

  override fun toString(): String {
    return "Coordinate(longitude=$longitude, latitude=$latitude, radiusOfEarth=$radiusOfEarth)"
  }
}
