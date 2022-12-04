package bg.tu_varna.sit;

public class Main {

  public static void main(String[] args) {

    String[] diseases = {"d1", "d2", "d3"};
    String[] allergies = {"a1", "a2", "a3"};
    try {
      Adult adult = new Adult("name", 20, diseases);
      Adult adult2 = new Adult("name2", 200, diseases);
      Kid kid = new Kid("name", 300, allergies);
      Kid[] kids = {kid};
      Adult[] adults = {adult, adult2};
      SocialKitchen<Adult> socialKitchen = new SocialKitchen<>(adults);
      socialKitchen.printAll();
      SocialKitchen<Kid> kidSocialKitchen = new SocialKitchen<>(kids);
      kidSocialKitchen.printAll();
      System.out.println(kidSocialKitchen.getAll().length);
    } catch (DataException e) {
      e.printStackTrace();
    }
  }
}
