package bg.tu_varna.sit.task4;

import java.util.Arrays;

/**
 * Задача 4
 * <p>
 * Подберете подходящи модификатори за достъп в задачата и
 * поставете сорс файловете в пакет bg.tu_varna.sit.task4.
 * <p>
 * Да се състави програма за недвижими имоти. За целта са необходими:
 * <p>
 * - Интерфейс ценови калкулатор (PriceCalculator) с метод,
 * който изчислява цената на имота (calculate);
 * <p>
 * - Енумерация за тип на имота (PropertyType) със стойности
 * ново строителство (NEW) и старо строителство (OLD);
 * <p>
 * - Енумерация за изложение на имота (Exposition) с възможните осем стойности;
 * <p>
 * - Абстрактен клас имот (Property) с атрибути за площ (area), базова цена
 * (basePrice) и тип (propertyType). Класът имплементира интерфейс ценови
 * калкулатор и интерфейс Comparable. Дефинирайте конструктор по всички полета,
 * методи за достъп, метод за равенство (по всички полета) и метод за текстово описание.
 * Интерфесният метод е по площ;
 * <p>
 * - Клас апартамент (Apartment), който разширява имота с атрибути за етаж
 * (floorNumber), брой стаи (numberOfRooms), изложение (exposition) и наличие
 * на паркомясто (hasParkingPlace). Дефинирайте конструктор по всички полета,
 * методи за достъп, метод за равенство по всички полета и метод за текстово описание.
 * Цената на даден апартамент се изчислява като:
 * <p>
 * -- базовата цена, увеличена с 25%, ако апартамента е ново строителство,
 * има изложение юг/запад/югозапад, площта му е повече от 50 кв.м. и има паркомясто;
 * <p>
 * -- базовата цена, увеличена с 20%, ако апартамента е
 * ново строителство и има изложение юг/запад/югозапад;
 * <p>
 * -- базовата цена, увеличена с 15%, ако апартамента е ново строителство;
 * -- базовата цена, увеличена с 18%, ако апартамента е старо строителство и има паркомясто;
 * -- базовата цена, увеличена с 12% във всички останали случаи;
 * <p>
 * - Клас къща (House), който разширява имота с атрибути брой етажи
 * (numberOfFloors) и наличие на градина (hasGarden).
 * Дефинирайте конструктор по всички полета, методи за достъп,
 * метод за равенство по всички полета и метод за текстово описание.
 * Цената на дадена къща се изчислява като:
 * <p>
 * -- базовата цена, увеличена с 20%, ако къщата е ново строителство
 * има градина и е с повече от един етаж;
 * -- базовата цена, увеличена с 10%, ако къщата има градина;
 * -- базовата цена, увеличена със 7% във всички останали случаи;
 * <p>
 * - Клас агенция за недвижими имоти (RealEstateAgency), който има като
 * атрибут списк от имоти (properties) и конструктор по този атрибут.
 * Методи:
 * <p>
 * -- за добавяне на имот (addProperty);
 * -- за изчисляване и връщане на цената на предлаганите имоти (calculatePriceOfAllProperties);
 * -- за сортиране на имотите по площ (sortPropertiesByArea);
 * -- за сортиране на имотите по цена (sortPropertiesByPrice);
 * -- за намиране и връщане броя имоти от даден тип (getNumberOfPropertiesByType);
 * -- за намиране и връщане броя предлагани къщи (getNumberOfAvailableHouses);
 * -- за намиране и връщане на най-скъпия предлаган апартамент (getMostExpensiveApartment);
 * -- за изчисляване и връщане средната цена на предлаганите къщи (calculateAverageHousePrice);
 * -- за текстово описание.
 * Дефинирайте клас Application с главна функция и тествайте описаните функционалности.
 */
public class Application {

  public static void main(String[] args) {

    Property apartment = new Apartment(90, 120000, PropertyType.NEW, 12, 3, Exposition.SOUTH, true);
    Property apartment2 = new Apartment(80, 110000, PropertyType.OLD, 2, 3, Exposition.WEST, false);
    Property house = new House(120, 160000, PropertyType.NEW, 3, true);
    Property house2 = new House(130, 170000, PropertyType.OLD, 4, false);

    Property[] properties = {apartment, apartment2, house, house2};
    RealEstateAgency realEstateAgency = new RealEstateAgency(Arrays.asList(properties));
    System.out.println(realEstateAgency.calculateAverageHousePrice());
    System.out.println(realEstateAgency.calculatePriceOfAllProperties());
    System.out.println(realEstateAgency.getMostExpensiveApartment());
    for (Property property : realEstateAgency.sortPropertiesByArea()) {
      System.out.println(property);
    }
    System.out.println(realEstateAgency.getNumberOfAvailableHouses());
    for (Property property : realEstateAgency.sortPropertiesByPrice()) {
      System.out.println(property);
    }


  }
}
