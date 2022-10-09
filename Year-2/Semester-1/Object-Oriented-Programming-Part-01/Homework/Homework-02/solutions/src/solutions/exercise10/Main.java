package exercise10;

/**
 * Задача 10
 * <p>
 * Дефинирайте клас MobilePhone, който описва мобилен телефон с атрибути марка,
 * памет, тегло и камера. Всички атрибути са символни низове. Като поведение използвайте
 * методи за четене/запис, текстово описание и равенство (по памет и камера).
 * Създайте масив от 10 обекта. Намерете и изведете средното тегло на телефоните
 * и телефона с най-добра камера.
 */
public class Main {

  private static final Integer PHONES_COUNT = 10;

  public static void main(String[] args) {

    MobilePhone[] mobilePhones = initializePhonesData(PHONES_COUNT);
    System.out.println("Mobile phones info:\n");
    for (MobilePhone mobilePhone : mobilePhones) {
      System.out.println(mobilePhone + "\n");
    }

    System.out.printf("Average phones weight: %.2f gr.%n" +
                    "Phone with the best camera:%n%s",
            getAveragePhonesWeight(mobilePhones),
            getPhoneWithBestCamera(mobilePhones));
  }

  private static MobilePhone[] initializePhonesData(int dataCounter) {

    String[] brands = {"iPhone", "Samsung", "Huawei", "Xiaomi", "Sony"};
    MobilePhone[] mobilePhones = new MobilePhone[dataCounter];

    for (int i = 0; i < dataCounter; i++) {
      mobilePhones[i] = new MobilePhone(
              brands[getRandomIntegerNumber(0, brands.length)],
              String.valueOf(getRandomIntegerNumber(128, 512)),
              String.valueOf(getRandomIntegerNumber(100, 200)),
              String.valueOf(getRandomIntegerNumber(20, 48))
      );

      for (int j = 0; j < i; j++) {
        if (mobilePhones[i].equals(mobilePhones[j])) {
          i--;
          break;
        }
      }
    }

    return mobilePhones;
  }

  private static double getAveragePhonesWeight(MobilePhone[] mobilePhones) {

    double totalWeight = 0;
    for (MobilePhone mobilePhone : mobilePhones) {
      totalWeight += Double.parseDouble(mobilePhone.getWeight());
    }

    return totalWeight / mobilePhones.length;
  }

  private static MobilePhone getPhoneWithBestCamera(MobilePhone[] mobilePhones) {

    MobilePhone max = mobilePhones[0];
    for (int i = 1; i < mobilePhones.length; i++) {
      if (Integer.parseInt(mobilePhones[i].getCamera()) > Integer.parseInt(max.getCamera())) {
        max = mobilePhones[i];
      }
    }

    return max;
  }

  private static int getRandomIntegerNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }
}
