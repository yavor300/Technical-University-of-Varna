package exercise10;

public class MobilePhone {
  
  private String brand;
  private String memory;
  private String weight;
  private String camera;

  public MobilePhone(String brand, String memory, String weight, String camera) {
    this.brand = brand;
    this.memory = memory;
    this.weight = weight;
    this.camera = camera;
  }

  @Override
  public String toString() {
    return String.format("Brand: %s%nMemory: %s GB%nWeight: %s gr.%nCamera: %s MPx",
            getBrand(), getMemory(), getWeight(), getCamera());
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MobilePhone mobilePhone = (MobilePhone) o;

    if (!getMemory().equals(mobilePhone.getMemory())) return false;
    return getCamera().equals(mobilePhone.getCamera());
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getMemory() {
    return memory;
  }

  public void setMemory(String memory) {
    this.memory = memory;
  }

  public String getWeight() {
    return weight;
  }

  public void setWeight(String weight) {
    this.weight = weight;
  }

  public String getCamera() {
    return camera;
  }

  public void setCamera(String camera) {
    this.camera = camera;
  }
}
