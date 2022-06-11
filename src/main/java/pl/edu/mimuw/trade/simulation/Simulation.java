package pl.edu.mimuw.trade.simulation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.agents.Agent;
import pl.edu.mimuw.trade.agents.Speculator;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.io.SkipSerialization;
import pl.edu.mimuw.trade.strategy.stock.StockStrategy;

import java.util.*;

/**
 * Centrum symulacji. Przeprowadza rundy przez kolejne etapy, przeprowadza symulację przez kolejne rundy
 */
public class Simulation {
  public static Random RANDOM = new Random();

  @SerializedName("dzien")
  @Expose(deserialize = false)
  private int lastDay;
  @SerializedName("ceny_srednie")
  @Expose(deserialize = false)
  private Map<String, Double> lastDayAvgPrices;
  @SerializedName("ceny_max")
  @Expose(deserialize = false)
  private Map<String, Double> lastDayMaxPrices;
  @SerializedName("ceny_min")
  @Expose(deserialize = false)
  private Map<String, Double> lastDayMinPrices;

  @SerializedName("gielda")
  @SkipSerialization
  private StockStrategy stockStrategy;
  @SerializedName("dlugosc")
  @SkipSerialization
  private int SIMULATION_LENGTH;
  @SerializedName("kara_za_brak_ubran")
  @SkipSerialization
  private int NO_CLOTHES_PENALTY;

  private static int day = 1;
  private static int noClothesPenalty;

  private transient SortedSet<Agent> agents;
  private transient LinkedList<Agent> dead;

  public static Stock stock;

  @SerializedName("ceny")
  @SkipSerialization
  private DayLog fallBack;

  public Simulation(StockStrategy stockStrategy) {
    this();
    this.stockStrategy = stockStrategy;
  }

  public Simulation() {
    this.agents = new TreeSet<>(Agent::compareTo);
    this.dead = new LinkedList<>();
  }

  public void init(LinkedList<Worker> workers, LinkedList<Speculator> speculators) {
    Simulation.stock = new Stock(stockStrategy, fallBack);
    this.agents.addAll(workers);
    this.agents.addAll(speculators);
    noClothesPenalty = NO_CLOTHES_PENALTY;
  }

  public static int day() {
    return day;
  }

  public static int noClothesPenalty() {
    return noClothesPenalty;
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
    lastDay = day;
    System.out.println(getCurrent());
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

  public int simulationLength() {
    return SIMULATION_LENGTH;
  }

  public Stock stock() {
    return stock;
  }

  public DayLog getCurrent() {
    return stock.log.getCurrent();
  }

  @Override
  public String toString() {
    return "Simulation: day " + day;
  }
}
