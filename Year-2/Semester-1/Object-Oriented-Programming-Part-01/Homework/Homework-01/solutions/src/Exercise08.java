/**
 * Задача 8
 * Закусвалня разполага с приготвени 289 банички. Пред нея се е заформила опашка,
 * като е установено, че на всеки трима чакащи първият си купува по две банички,
 * вторият – по четири, а третият – една. Разпишете програма, с която да се установи
 * колко души ще могат да си купят закуски от закусвалнята. Последният, за който има
 * налични количества, ще успее ли да купи всички закуски, които е планирал?
 */
public class Exercise08 {

  private static final Integer PRODUCT_COUNT = 289;
  private static final Integer GROUP_BUY_COUNT = 7;
  private static final Integer PEOPLE_IN_GROUP = 3;

  public static void main(String[] args) {
    int buyersCount = (PRODUCT_COUNT / GROUP_BUY_COUNT) * PEOPLE_IN_GROUP;
    int leftProducts = PRODUCT_COUNT - (PRODUCT_COUNT / GROUP_BUY_COUNT) * GROUP_BUY_COUNT;
    boolean isLastOrderSuccessful = false;

    if (leftProducts > 0) {
      int productsForLastCustomer = 0;
      int lastCustomerOrder = 0;

      while (leftProducts > 0) {
        lastCustomerOrder++;
        if (lastCustomerOrder % 3 == 0) {
          leftProducts -= 1;
          productsForLastCustomer = 1;
        } else if (lastCustomerOrder % 2 == 0) {
          if (leftProducts < 4) {
            productsForLastCustomer = 4 - leftProducts;
            leftProducts = 0;
          } else {
            leftProducts -= 4;
            productsForLastCustomer = 4;
          }
        } else {
          if (leftProducts < 2) {
            productsForLastCustomer = 2 - leftProducts;
            leftProducts = 0;
          } else {
            leftProducts -= 2;
            productsForLastCustomer = 2;
          }
        }
        buyersCount++;
      }

      if (lastCustomerOrder == 2 && productsForLastCustomer == 4 || lastCustomerOrder == 1 && productsForLastCustomer == 2 || lastCustomerOrder == 3) {
        isLastOrderSuccessful = true;
      }
    }

    System.out.printf("%d хора ще си купят банички.%n", buyersCount);
    if (isLastOrderSuccessful) {
      System.out.println("Последният купувач ще купи необходимия брой банички.");
    } else {
      System.out.println("Последният купувач няма да купи необходимия брой банички.");
    }
  }
}
