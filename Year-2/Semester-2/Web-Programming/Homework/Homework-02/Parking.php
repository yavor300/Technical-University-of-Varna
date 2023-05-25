<?php

class Parking {
  protected $maxCapacity;
  protected $vehicles = [];

  public function __construct($maxCapacity) {
    $this->maxCapacity = $maxCapacity;
  }

  public function addVehicle(Vehicle $vehicle) {
    if (count($this->vehicles) < $this->maxCapacity) {
      array_push($this->vehicles, $vehicle);
      return true;
    }
    return false;
  }

  public function removeVehicle() {
    if (count($this->vehicles) > 0) {
      array_pop($this->vehicles);
      return true;
    }
    return false;
  }

  public function getTotalParkingFee() {
    $totalFee = 0;
    foreach ($this->vehicles as $vehicle) {
      $totalFee += $vehicle->getParkingFee();
    }
    return $totalFee;
  }

  public function getAvailableSpaces() {
    return $this->maxCapacity - count($this->vehicles);
  }
}
