package rail_transport

abstract class Locomotive : Expense {

  abstract fun getType(): LocomotiveType
}