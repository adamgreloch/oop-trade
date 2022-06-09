package pl.edu.mimuw.trade.stock;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.agents.Agent;
import pl.edu.mimuw.trade.agents.Speculator;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.strategy.stock.StockStrategy;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

/**
 * Centrum symulacji. Przeprowadza rundy przez kolejne etapy, przeprowadza symulację przez kolejne rundy
 */
public class Simulation {

  public static Random RANDOM = new Random();
  private static int day = 1;
  private transient final LinkedList<Agent> agents;
  @SerializedName("robotnicy")
  private final LinkedList<Worker> workers;
  @SerializedName("spekulanci")
  private final LinkedList<Speculator> speculators;
  private transient final LinkedList<Worker> deadWorkers;
  private final Stock stock;
  private transient int lastId = 0;

  public Simulation(StockStrategy stockStrategy) {
    this.agents = new LinkedList<>();
    this.workers = new LinkedList<>();
    this.speculators = new LinkedList<>();
    this.deadWorkers = new LinkedList<>();
    this.stock = new Stock(this, stockStrategy);
  }

  public static int day() {
    return day;
  }

  public void addWorkers(Collection<Worker> workers) {
    this.workers.addAll(workers);
    this.agents.addAll(workers);
  }

  public void addSpeculators(Collection<Speculator> speculators) {
    this.speculators.addAll(speculators);
    this.agents.addAll(speculators);
  }

  public void run(int duration) {
    while (day <= duration) {
      agents.forEach(Agent::act);

      agents.forEach(Agent::makeOffers);
      stock.processTransactions();

      agents.forEach(Agent::finishDay);

      checkForDeaths();
      System.out.println(stock.getDayLog());
      day++;
      stock.newDay();
    }
  }

  public void checkForDeaths() {
    for (Worker worker : workers)
      if (worker.isDead()) {
        deadWorkers.add(worker);
        System.out.println(worker + " died on day " + day);
      }

    for (Worker deadWorker : deadWorkers) {
      agents.remove(deadWorker);
      workers.remove(deadWorker);
    }
  }

  public Stock stock() {
    return stock;
  }

  public int assignId() {
    return lastId++;
  }
}
