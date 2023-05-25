<?php

abstract class Vehicle {
  protected $productionYear;

  public function __construct($productionYear) {
    $this->productionYear = $productionYear;
  }

  abstract public function getParkingFee();

  public function getProductionYear() {
    return $this->productionYear;
  }
}
