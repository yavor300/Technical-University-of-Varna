package bg.tu_varna.sit.task2;

/**
 * Задача 2
 * <p>
 * Подберете подходящи модификатори за достъп в задачата и поставете сорс
 * файловете в пакет bg.tu_varna.sit.task2
 * Да се създаде програма, описваща агенция за недвижими имоти.
 * За целта са необходими:
 * - Интерфейс Commission с метод calculateCommission(), който изчислява и
 * връща комисионната за даден имот;
 * - Клас за описание на грешка InvalidDataException с подходящо съобщение
 * за грешка, при инициализиране на обекти с невалидни стойности;
 * - Енумерация за тип на имота (PropertyType) със стойности за отдаване под
 * наем (rent), за продажба (sale) и неопределен (undefined);
 * - Енумерация за паркинг () с атрибути, указващи броя места за паркиране
 * (numberOfParkingPlaces) и тип на имота (за отдаване под наем или за продажба).
 * Стойностите са: няма паркинг (noParkingLot), едно място за отдаване под наем
 * (onePlaceForRent), едно място за продажба (onePlaceForSale), две места за
 * отдаване под наем (twoPlacesForRent), две места за продажба (twoPlacesForSale),
 * места за отдаване под наем (placesForRent) и места за продажба (placesForSale).
 * Последните две стойности имат като дефолтен брой места 100;
 * - Абстрактен клас Property, който имплементира интерфейс Commission. Класът
 * има атрибути площ(area), цена(price) и тип на имота (propertyType). Дефинирайте
 * необходимите конструктор и методи за достъп;
 * - Клас Apartment, наследник на Property, който има като атрибути брой стаи
 * (numberOfRooms), етаж (numberOfRooms) и паркинг (parkingLot). Комисионната
 * се изчислява като 15% от цената ако апартамента е за отдаване под наем и има
 * едно или две паркоместа за отдаване под наем; 10% ако площта му е по-малка от
 * 60 кв.м. и 7% от цената в останалите случаи;
 * - Клас House, наследник на Property, който има като атрибути брой етажи (numberOfFloors)
 * и атрибут, указващ наличието на градина (hasGarden). Комисионната се изчислява като 8%
 * ако къщата се отдава под наем и има градина; 5% ако къщата е за продажба и площта е
 * по-малка от 100 кв.м. и 3% от цената в останалите случаи;
 * - Клас Office, наследник на Property, който има атрибути паркинг (parkingLot) и брой
 * стаи (numberOfRooms). При създаване на обекта е необходима проверка за съвпадение на
 * типа имот. Комисионната се изчислява като 18% от цената ако офисът е за отдаване под
 * наем, 15% ако има паркинг и повече от две стаи, и 11% във всички останали случаи;
 * - Клас Shop, наследник на Property. Комисионната се изчислява като 2% ако магазинът
 * е за отдаване под наем и площта му е по-малка от 50 кв.м., 1% ако площта му е над
 * 100 кв.м. и 6% във всички останали случаи.
 * Създайте клас RealEstateAgency, който има полета за име (name) и масив от недвижими
 * имоти (properties). Дефинирайте необходимите конструктор, методи за достъп и методи:
 * - calculateTotalExpectedCommission(), който изчислява и връща очакваната комисионна
 * от всички предлагани имоти;
 * - getPropertiesForSale(), който намира и връща броя недвижими имоти, предлагани от
 * агенцията за продажба.
 * Дефинирайте клас Application с главна функция и тествайте описаните функционалности.
 */
public class Application {

  public static void main(String[] args) {

    Property apartment = new Apartment(75.00, 120000, PropertyType.sale, 2, 1, ParkingLot.onePlaceForSale);
    Property house = new House(150.00, 220000, PropertyType.rent, 2, ParkingLot.twoPlacesForSale, true);
    Property office = new Office(750.00, 520000, PropertyType.sale, ParkingLot.placesForRent, 20);
    Property shop = new Shop(200.00, 7000, PropertyType.rent);

    Property[] properties = {apartment, house, office, shop};

    RealEstateAgency realEstateAgency = new RealEstateAgency("TU", properties);

    System.out.println(realEstateAgency.calculateTotalExpectedCommission());
    System.out.println(realEstateAgency.getPropertiesForSale());

    try {
      Property invalidShop = new Shop(-1, -2, null);
    } catch (InvalidDataException e) {
      System.out.println(e.getMessage());
    }

  }
}
