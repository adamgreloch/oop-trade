package pl.edu.mimuw.trade.simulation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.agents.Agent;
import pl.edu.mimuw.trade.agents.Speculator;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.strategy.stock.StockStrategy;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

/**
 * Centrum symulacji. Przeprowadza rundy przez kolejne etapy, przeprowadza symulację przez kolejne rundy
 */
public class Simulation {
  public static Random RANDOM = new Random();

  @Expose(deserialize = false)
  private static int day = 1;
  @SerializedName("gielda")
  private StockStrategy stockStrategy;
  @SerializedName("dlugosc")
  @Expose(serialize = false)
  private final int SIMULATION_LENGTH = 5;
  @SerializedName("kara_za_brak_ubran")
  private final int NO_CLOTHES_PENALTY = 2;

  private transient LinkedList<Agent> agents;
  private transient LinkedList<Agent> dead;

  public static transient Stock stock;

  @SerializedName("ceny_srednie")
  private Map<String, Double> lastDayAvgPrices;
  @SerializedName("ceny_max")
  private Map<String, Double> lastDayMaxPrices;
  @SerializedName("ceny_min")
  private Map<String, Double> lastDayMinPrices;
  @SerializedName("ceny")
  private DayLog fallBack;

  public Simulation(StockStrategy stockStrategy) {
    this.agents = new LinkedList<>();
    this.stockStrategy = stockStrategy;
    this.dead = new LinkedList<>();
  }

  public Simulation() {
    this.agents = new LinkedList<>();
    this.dead = new LinkedList<>();
  }

  public void init(LinkedList<Worker> workers, LinkedList<Speculator> speculators) {
    Simulation.stock = new Stock(stockStrategy, fallBack);
    this.agents.addAll(workers);
    this.agents.addAll(speculators);
  }

  public static int day() {
    return day;
  }

  public void runDay() {
    agents.forEach(Agent::act);

    agents.forEach(Agent::makeOffers);
    stock.processTransactions();

    agents.forEach(Agent::finishDay);

    checkForDeaths();
    lastDayMaxPrices = stock.log.mapLastMaxPrices();
    lastDayMinPrices = stock.log.mapLastMinPrices();
    lastDayAvgPrices = stock.log.mapLastAvgPrices();
    day++;
    stock.newDay();
  }

  public void checkForDeaths() {
    for (Agent agent : agents)
      if (agent.isDead()) {
        dead.add(agent);
        System.out.println(agent + " died on day " + day);
      }

    for (Agent deadAgent : dead)
      agents.remove(deadAgent);
  }

  public void addAgents(LinkedList<? extends Agent> agents) {
    this.agents.addAll(agents);
  }

  public int simulationLength() {
    return SIMULATION_LENGTH;
  }

  public Stock stock() {
    return stock;
  }

  public DayLog getCurrent() {
    return stock.log.getCurrent();
  }
}
