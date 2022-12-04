package bg.tu_varna.sit.task2;

/**
 * Задача 2
 *
 * Подберете подходящи модификатори за достъп в задачата и
 * поставете сорс файловете в пакет bg.tu_varna.sit.task2.
 * Създайте програма, която ще подпомогне управлението на
 * архива на документацията във фирма Х. Документацията се
 * архивира на хартиен носител, диск, USB памет и други носители.
 * Носителите се съхраняват физически в помещение с подходящи условия
 * за опазване на тяхната цялост и пригодност. Помещението е разделено
 * на секции, като във всяка от тях се съхраняват документи на еднотипни носители.
 * Създайте клас документ, съхранен на хартиен носител (PaperDocument)
 * с описание за заглавие на документа (title), дата на съставяне
 * (съхранете като текст във формат yy-mm-dd) (dateCreated), брой страници
 * (pages) и достъп (access), като вариантите на достъп (Access) са free и
 * restricted. Документите на хартиен носител да се
 * сравняват по тяхното заглавие и дата на съставяне.
 * Съставете клас за съхранение на документ на диск (DiscStorage),
 * като всеки диск има идентификатор (id) (цяло число) и текстово описание
 * на съдържанието му (content). Дисковете се сравняват по техния идентификатор.
 * Създайте параметризиран клас секция (Section) с полета наименование (title) и
 * параметризиран масив от документи (documents). Добавете метод за търсене на документ
 * в секцията (findDocument), който приема параметризиран обект и връща текст
 * „Открит“ или „Не е намерен“, в зависимост от резултата.
 * Дефинирайте клас Application с главна функция и тествайте описаните функционалности.
 */
public class Application {

  public static void main(String[] args) {

    DiscStorage discStorage = new DiscStorage(1, "content");
    DiscStorage discStorage2 = new DiscStorage(2, "content");
    DiscStorage[] discStorages = {discStorage};
    Section<DiscStorage> discStorageSection = new Section<>("section", discStorages);
    System.out.println(discStorageSection.findDocument(discStorage));
    System.out.println(discStorageSection.findDocument(discStorage2));
  }
}
