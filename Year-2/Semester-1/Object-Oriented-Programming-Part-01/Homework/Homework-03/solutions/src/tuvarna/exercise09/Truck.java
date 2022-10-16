package tuvarna.exercise09;

public class Truck extends Car {

  private final int weightCapacity;
  private double workingEngineHours;

  public Truck(String brand, String model, String color, int power, String engine, String transmission, int productionYear, int weightCapacity, double workingEngineHours) {
    super(brand, model, color, power, engine, transmission, productionYear);
    this.weightCapacity = weightCapacity;
    this.workingEngineHours = workingEngineHours;
  }

  public boolean canStartEngine() {
    return workingEngineHours < 6;
  }

  public int getWeightCapacity() {
    return weightCapacity;
  }

  public double getWorkingEngineHours() {
    return workingEngineHours;
  }

  public void setWorkingEngineHours(double workingEngineHours) {
    this.workingEngineHours = workingEngineHours;
  }
}
