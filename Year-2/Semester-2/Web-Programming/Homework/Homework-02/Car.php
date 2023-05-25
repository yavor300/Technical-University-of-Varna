<?php

require 'Vehicle.php';

class Car extends Vehicle {
  protected $type;

  public function __construct($productionYear, $type) {
    parent::__construct($productionYear);
    $this->type = $type;
  }

  public function getParkingFee() {
    switch ($this->type) {
      case 'sedan':
      case 'cabrio':
        return $this->productionYear * 2;
      case 'combi':
        return $this->productionYear * 3;
      default:
        return 0;
    }
  }

  public function getType() {
    return $this->type;
  }
}