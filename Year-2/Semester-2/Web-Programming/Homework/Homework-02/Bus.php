<?php

class Bus extends Vehicle {
  protected $maxPassengers;

  public function __construct($productionYear, $maxPassengers) {
    parent::__construct($productionYear);
    $this->maxPassengers = $maxPassengers;
  }

  public function getParkingFee() {
    return $this->productionYear * $this->maxPassengers;
  }

  public function getMaxPassengers() {
    return $this->maxPassengers;
  }
}
