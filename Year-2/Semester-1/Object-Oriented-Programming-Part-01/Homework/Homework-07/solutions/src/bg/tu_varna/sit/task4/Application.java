package bg.tu_varna.sit.task4;

/**
 * Задача 4
 * <p>
 * Подберете подходящи модификатори за достъп в задачата и поставете
 * сорс файловете в пакет bg.tu_varna.sit.task4
 * Съставете програма, която има за цел да изчислява добива на риба
 * от плавателните водни басейни на дадена територия.
 * Предвидете клас FishList, съдържащ списък на всички риби, обитаващи
 * територията. За казуса използвайте рибите ester, catfish, perch, tench, piranha.
 * Създайте клас риба (Fish) с полета наименование (name), приемащо стойности
 * от представения по-горе списък, и количество от нея по експертна оценка,
 * тона (quantity). Класът се разширява от:
 * • ядлива риба (EdibleFish), която допълва информацията с процент от популацията,
 * която е годна за улов (възрастни индивиди) (percentOfYield);
 * • негодна за консумация риба (NonEdibleFish), където допълнително като текст
 * се описва причината тя да е неизползваема или да е заплаха за здравето (threat).
 * Създайте интерфейс Usage, който да съдържа boolean метод isProductabe().
 * Интерфейсът да се имплементира от клас воден басейн (WaterBody). Водният
 * басейн се описва с наименование (name), дълбочина (depth) и масив от риби, които го обитават.
 * Имплементира метода isProductabe(), където за потенциално продуктивен се
 * счита басейн, който е обитаван от поне една годна за консумация риба с
 * количества, които разрешават нейния улов (над 10 тона).
 * Да се добави абстрактно поведение isFloaty(), чието предназначение е да
 * определи дали водният басейн е плаваем или не.
 * Да се допълни с метод calculateProduction(), който да изчислява възможно
 * най-големите количества риба, които законно могат да се уловят от плавателния басейн.
 * WaterBody се наследява от:
 * • Езеро (Lake) с широчина (width) и дължина (length). Плаваемо е, ако
 * дълбочината му е поне 5 м., а широчината и дължината – поне 1000 м.
 * • Река (River) със скорост на течението (km/h) (speed). Плаваема е, ако
 * дълбочината е поне 3 м. и скоростта на течението не надвишава 30 km/h.
 * Създайте клас за изключения, които възникват в класа WaterBody (WaterBodyException).
 * Класът за изключенията да приема съобщение за грешка като параметър на конструктора си.
 * Приложете класа за изключения WaterBodyException в параметризирания конструктор на
 * класа WaterBody. Да обработва подадени отрицателни стойности на полето depth, като извежда
 * съобщение "Дълбочината не може да бъде отрицателна величина".
 * Предайте изключението към конструкторите на класовете Lake и River.
 */
public class Application {

  public static void main(String[] args) {

  }
}