import rail_transport.Locomotive
import rail_transport.Wagon
import rail_transport.DieselLocomotive
import rail_transport.ElectricLocomotive
import rail_transport.FreightWagon
import rail_transport.PassengerWagon
import rail_transport.SteamLocomotive
import rail_transport.Coordinate
import rail_transport.Station
import rail_transport.Town
import rail_transport.Train

fun main() {

  val passengerWagon: Wagon = PassengerWagon()
  val freightWagon: Wagon = FreightWagon()

  val electricLocomotive: Locomotive = ElectricLocomotive()
  val steamLocomotive: Locomotive = SteamLocomotive()
  val dieselLocomotive: Locomotive = DieselLocomotive()

  val townVarna = Town("Varna", "9000")
  println(townVarna)
  val townYambol = Town("Yambol", "8000")
  println(townYambol)

  val coordinateVarnaStation = Coordinate(10.0, 10.0);
  println(coordinateVarnaStation)
  val coordinateYambolStation = Coordinate(20.0, 20.0);
  println(coordinateYambolStation)

  val stationVarna = Station("Varna", townVarna, coordinateVarnaStation)
  println(stationVarna)
  val stationYambol = Station("Yambol", townYambol, coordinateYambolStation)
  println(stationYambol)

  val wagons = hashSetOf(passengerWagon, freightWagon)
  val train = Train(electricLocomotive, wagons)
  train.setStations(stationVarna, stationYambol)
  train.calculate().also { println("Train calculation: $it") }
  train.setStations(stationYambol, stationVarna)
  train.calculate().also { println("Train calculation: $it") }
}
