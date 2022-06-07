package pl.edu.mimuw.trade.stock;

import pl.edu.mimuw.trade.agents.Agent;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Centrum symulacji. Przeprowadza rundy przez kolejne etapy, przeprowadza symulację przez kolejne rundy
 */
public class Simulation {

  public static Random RANDOM = new Random();
  private static int day = 1;
  private final LinkedList<Agent> active;
  private final LinkedList<Agent> dead;
  private final Stock stock;
  private int lastId = 0;

  public Simulation(StockStrategy stockStrategy) {
    this.active = new LinkedList<>();
    this.dead = new LinkedList<>();
    this.stock = new Stock(this, stockStrategy);
  }

  public static int day() {
    return day;
  }

  public void addAgents(Agent... agents) {
    active.addAll(List.of(agents));
  }

  public void run(int duration) {
    while (day <= duration) {
      active.forEach(Agent::act);

      active.forEach(Agent::makeOffers);
      stock.processTransactions();

      active.forEach(Agent::finishDay);

      checkForDeaths();
      System.out.println(stock.getDayLog());
      day++;
      stock.newDay();
    }
  }

  public void checkForDeaths() {
    for (Agent agent : active)
      if (agent.isDead()) {
        dead.add(agent);
        System.out.println(agent + " died on day " + day);
      }

    for (Agent deadAgent : dead)
      active.remove(deadAgent);
  }

  public Stock stock() {
    return stock;
  }

  public int assignId() {
    return lastId++;
  }
}
