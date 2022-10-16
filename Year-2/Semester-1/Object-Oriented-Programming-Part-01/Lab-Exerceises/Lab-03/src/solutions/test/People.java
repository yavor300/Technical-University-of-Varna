package solutions.test;

public class People {

  private int age;
  private char gender;
  private double hipCircumference;
  private double shoulderGirth;
  private double neckCircumference;
  private double calfCircumference;

  public People() {
    this.age = 18;
    this.gender = 'G';
    this.hipCircumference = 15.9;
    this.shoulderGirth = 89.7;
    this.neckCircumference = 38.6;
    this.calfCircumference = 49.8;
  }

  public People(int age, char gender, double hipCircumference, double shoulderGirth, double neckCircumference, double calfCircumference) {
    this.age = age;
    this.gender = gender;
    this.hipCircumference = hipCircumference;
    this.shoulderGirth = shoulderGirth;
    this.neckCircumference = neckCircumference;
    this.calfCircumference = calfCircumference;
  }

  public double getBodyMassIndex() {
    return hipCircumference / (neckCircumference + calfCircumference + shoulderGirth);
  }

  @Override
  public String toString() {
    return String.format("Данни за участник:%n" +
                    "Възраст - %d%nПол - %c%n" +
                    "Бедрена обиколка - %.2f см.%n" +
                    "Раменна обиколка - %.2f см.%n" +
                    "Обиколка на шията - %.2f см.%n" +
                    "Обиколка на прасеца - %.2f см.",
            getAge(), getGender(), getHipCircumference(), getShoulderGirth(),
            getNeckCircumference(), getCalfCircumference());
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public char getGender() {
    return gender;
  }

  public void setGender(char gender) {
    this.gender = gender;
  }

  public double getHipCircumference() {
    return hipCircumference;
  }

  public void setHipCircumference(double hipCircumference) {
    this.hipCircumference = hipCircumference;
  }

  public double getShoulderGirth() {
    return shoulderGirth;
  }

  public void setShoulderGirth(double shoulderGirth) {
    this.shoulderGirth = shoulderGirth;
  }

  public double getNeckCircumference() {
    return neckCircumference;
  }

  public void setNeckCircumference(double neckCircumference) {
    this.neckCircumference = neckCircumference;
  }

  public double getCalfCircumference() {
    return calfCircumference;
  }

  public void setCalfCircumference(double calfCircumference) {
    this.calfCircumference = calfCircumference;
  }
}
