package pl.edu.mimuw;

import pl.edu.mimuw.agents.Agent;
import pl.edu.mimuw.stock.Stock;
import pl.edu.mimuw.stock.StockStrategy;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Centrum symulacji. Przeprowadza rundy przez kolejne etapy, przeprowadza symulację przez kolejne rundy
 */
public class Simulation {

  public static Random RANDOM = new Random();
  private static int day = 1;
  private final Set<Agent> active;
  private final Set<Agent> dead;
  private final Stock stock;
  private int lastId = 0;

  public Simulation(StockStrategy stockStrategy) {
    this.active = new HashSet<>();
    this.dead = new HashSet<>();
    this.stock = new Stock(this, stockStrategy);
  }

  public static int day() {
    return day;
  }

  public void addAgents(Set<Agent> agents) {
    active.addAll(agents);
  }

  public void run(int duration) {
    while (day <= duration) {
      active.forEach(Agent::act);
      stock.processTransactions();
      day++;
    }
  }

  public void moveToDead(Agent agent) {
    active.remove(agent);
    dead.add(agent);
    System.out.println(agent + " died");
  }

  public Stock stock() {
    return stock;
  }

  public int assignId() {
    return lastId++;
  }
}
