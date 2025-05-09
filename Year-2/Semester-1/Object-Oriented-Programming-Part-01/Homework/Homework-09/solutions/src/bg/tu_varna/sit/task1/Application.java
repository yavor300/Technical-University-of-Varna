package bg.tu_varna.sit.task1;

/**
 * Задача 1
 * <p>
 * Подберете подходящи модификатори за достъп в задачата и поставете
 * сорс файловете в пакет bg.tu_varna.sit.task1.
 * Да се състави програма за резервиране на хотелски стаи.
 * <p>
 * За целта са необходими:
 * <p>
 * - Интерфейс ценови калкулатор (PriceCalculator) с методи за изчисляване и
 * връщане стойностите на престоя (calculateStayPrice), възможната отстъпка(discount)
 * и крайната цена на резервация (calculateReservationPrice);
 * <p>
 * - Енумерация за изложение (Exposure) със стойности морска
 * гледка (SEA_VIEW) и изглед към парк (PARK_VIEW);
 * <p>
 * - Абстрактен клас стая (Room), имплементиращ интерфейс ценови калкулатор.
 * Класът има атрибути цена на нощувка (pricePerDay), изложение (exposure) и
 * дали стаята е заета (available). Дефинирайте конструктор по цена на нощувка
 * и изложение, като по подразбиране стаята е свободна. Дефинирайте методи за достъп
 * и метод за модификация на атрибута, указващ дали стаята е свободна или не.
 * Имплементирайте метода, изчисляващ крайната цена на резервация
 * като разлика от стойността на престоя и възможната отстъпка;
 * <p>
 * - Клас единична стая (SingleRoom), който разширява стая с атрибут брой дни (days).
 * Дефинирайте конструктор по всички полета, методи за достъп и текстово описание.
 * Цената на резервацията се изчислява като произведение от броя дни и цената за нощувка;
 * при резервация за повече от 3 дни се прави 10% отстъпка;
 * <p>
 * - Клас двойна стая (DoubleRoom), който разширява стая с атрибути брой дни(days)
 * и дали ще има деца (hasChildren). Дефинирайте конструктор по всички полета,
 * методи за достъп и метод за текстово описание. Цената на резервацията се
 * изчислява като произведение от броя дни и цената на нощувка, увеличена с 10.
 * Има отстъпка 15% от цената ако ще има деца и нощувките ще са повече от 5;
 * <p>
 * - Клас хотел (Hotel), който има като атрибут списък от стаи и конструктор по подразбиране.
 * Методи:
 * <p>
 * -- за добавяне на стая (addRoom);
 * <p>
 * -- за създаване на резервация (createReservation), който проверява
 * дали има свободна стая от желания тип и маркира първата срещната такава като заета;
 * <p>
 * -- изчисляване и връщане стойността на отстъпката от всички заети стаи (calculateBookedRoomsDiscount);
 * <p>
 * -- изчисляване и връщане средната стойност от цената на свободните
 * стаи с морски изглед (calculateAveragePriceOfAvailableRoomsWithSeaView);
 * <p>
 * -- за текстово описание.
 * <p>
 * Дефинирайте клас Application с главна функция и тествайте описаните функционалности.
 */
public class Application {

  public static void main(String[] args) {

    Room singleRoom = new SingleRoom(40, Exposure.SEA_VIEW, 5);
    Room doubleRoom = new DoubleRoom(60, Exposure.PARK_VIEW, 7, true);

    Hotel hotel = new Hotel();
    hotel.addRoom(singleRoom);
    hotel.addRoom(doubleRoom);

    System.out.println(hotel.createReservation(Exposure.SEA_VIEW));
    System.out.println(hotel.calculateAveragePriceOfAvailableRoomsWithSeaView());
    System.out.println(hotel.calculateBookedRoomsDiscount());
    System.out.println(hotel);
  }
}
