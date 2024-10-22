package rail_transport

import java.lang.IllegalArgumentException
import kotlin.math.abs

class Train(private val locomotive: Locomotive, private val wagons: HashSet<Wagon>) : Expense {

  private var startStation: Station? = null
    get() = field
    set(value) {
      field = value
    }

  private var endStation: Station? = null
    get() = field
    set(value) {
      field = value
    }

  fun setStations(from: Station, to: Station) {
    startStation = from
    endStation = to
  }

  override fun calculate(): Double {

    if (endStation == null || startStation == null) {
      throw IllegalArgumentException("Both end station and start station must be declared. Use setStations().")
    }

    return (locomotive.calculate() + (locomotive.calculate() * wagons.size)) *
        (abs(startStation!!.coordinate.getDistance(endStation!!.coordinate)))
  }
}
