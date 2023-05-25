package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.impl.MakeIceCreamCommand;
import bg.tu_varna.sit.b1.f21621577.entities.IceCreamBuilder;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Използвайте Chain of Responsibility и Command моделите, за да съставите програма
 * за създавае на сладолед. Подават се списък с команди за добавяне на топки сладолед,
 * Ванилия, Шоколад, Пъпеш, Сметана, командите се подават към потока за съставяне на
 * сладолед, всяка стъпка добавя вида сладолед, за който отговаря, към фунийка или предава
 * фунийката към следващия етап. В края на потока се връща фунийка с избраните топки сладолед
 */
public class Application {

  public static void main(String[] args) {

    IceCreamBuilder iceCreamBuilder = new IceCreamBuilder(new ArrayList<>(Arrays.asList("Ванилия", "Шоколад", "Пъпеш", "Сметана")));

    Command command = new MakeIceCreamCommand(iceCreamBuilder);
    System.out.println(command.execute());
  }
}
