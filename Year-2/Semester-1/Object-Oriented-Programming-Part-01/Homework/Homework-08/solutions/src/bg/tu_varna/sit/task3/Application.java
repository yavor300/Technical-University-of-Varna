package bg.tu_varna.sit.task3;


/**
 * Задача 3
 * <p>
 * Подберете подходящи модификатори за достъп в задачата и поставете
 * сорс файловете в пакет bg.tu_varna.sit.task3.
 * Да се състави програма за талон за отстъпка.
 * За целта са необходими:
 * - Интерфейс отстъпка (Discount) с методи за изчисляване на общата
 * отстъпка (calculateTotalDiscount) и за изчисляване на средната
 * отстъпка (calculateAverageDiscount);
 * - Енумерация за възрастова група (AgeGroup) със стойности дете (CHILD),
 * младеж (TEENAGE), възрастен (ADULT) и пенсионер (PENSIONER);
 * - Клас Потребител (Person) с атрибути име (name), възрастова група
 * (ageGroup) и цена на закупения продукт (productPrice).
 * Дефинирайте конструктор по всички полета и методи за достъп. Създайте
 * и метод, който изчислява и връща базова отстъпка (calculateBaseDiscount),
 * който връща 8 процента от цената на продукта, ако потребителят е дете или
 * пенсионер, и 3 процента в останалите случаи;
 * - Клас отстъпка за храна (FoodDiscount), който имплементира интерфейс
 * отстъпка и има като атрибути масив от потребители (people) и процент на
 * отстъпка за храна (foodDiscountRate). Дефинирайте конструктор по всички полета и методи за достъп.
 * Имплементирайте интерфейсните методи;
 * - Клас отстъпка за напитка (DrinkDiscount), който имплементира интерфейс
 * отстъпка и има като атрибути масив от потребители (people) и процент отстъпка
 * за напитка (drinkDiscountRate). Дефинирайте конструктор по всички полета и методи за достъп.
 * При имплементацията на интерфейсните методи се отчитат само потребителите от
 * възрастови групи възрастен и пенсионер;
 * - Клас отстъпка за игра (GameDiscount), който имплементира интерфейс отстъпка
 * и има като атрибути масив от потребители (people), процент отстъпка (discountRate)
 * и процент отстъпка за възрастни (discountRateForAdults). Дефинирайте конструктор
 * по всички полета и методи за достъп. Имплементирайте интерфейсните методи.
 * Създайте генерик клас за талон за отстъпка (Coupon). Дефинирайте в него генерик
 * методи за извеждане на общата отстъпка (displayTotalDiscount) и за извеждане на
 * средната отстъпка (displayAverageDiscount).
 * Дефинирайте клас Application с главна функция и тествайте описаните функционалности.
 */
public class Application {

  public static void main(String[] args) {

    Person person1 = new Person("person1", AgeGroup.adult, 10);
    Person person2 = new Person("person2", AgeGroup.child, 15);
    Person person3 = new Person("person3", AgeGroup.pensioner, 20);
    Person person4 = new Person("person4", AgeGroup.teenage, 25);
    Person[] people = {person1, person2, person3, person4};

    FoodDiscount foodDiscount = new FoodDiscount(people, 5);
    GameDiscount gameDiscount = new GameDiscount(people, 15, 5);

    Coupon<FoodDiscount> foodDiscountCoupon = new Coupon<>(foodDiscount);
    foodDiscountCoupon.displayAverageDiscount();
    foodDiscountCoupon.displayTotalDiscount();

    Coupon<GameDiscount> gameDiscountCoupon = new Coupon<>(gameDiscount);
    gameDiscountCoupon.displayAverageDiscount();
    gameDiscountCoupon.displayTotalDiscount();
  }
}
