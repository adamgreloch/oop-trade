package pl.edu.mimuw.trade.simulation;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.agents.Agent;
import pl.edu.mimuw.trade.agents.Speculator;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.strategy.stock.StockStrategy;

import java.util.LinkedList;
import java.util.Random;

/**
 * Centrum symulacji. Przeprowadza rundy przez kolejne etapy, przeprowadza symulację przez kolejne rundy
 */
public class Simulation {

  public static Random RANDOM = new Random();
  private static int day = 1;
  @SerializedName("gielda")
  private final StockStrategy stockStrategy;
  @SerializedName("dlugosc")
  private final int SIMULATION_LENGTH = 5;
  @SerializedName("kara_za_brak_ubran")
  private final int NO_CLOTHES_PENALTY = 2;

  @SerializedName("przebieg")
  private final StockLog log;

  private transient final LinkedList<Agent> agents;
  private transient final LinkedList<Worker> workers;
  private transient final LinkedList<Speculator> speculators;
  private transient final LinkedList<Worker> deadWorkers;
  private transient final Stock stock;

  public Simulation(StockStrategy stockStrategy, LinkedList<Worker> workers, LinkedList<Speculator> speculators) {
    this.workers = workers;
    this.speculators = speculators;

    this.agents = new LinkedList<>();
    this.agents.addAll(workers);
    this.agents.addAll(speculators);

    this.stockStrategy = stockStrategy;
    this.deadWorkers = new LinkedList<>();
    this.log = new StockLog();
    this.stock = new Stock(stockStrategy, this.log);
  }

  public static int day() {
    return day;
  }

  public void run() {
    while (day <= SIMULATION_LENGTH) {
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
}
