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
  public static Stock stock;
  private static int day = 1;
  private static int noClothesPenalty;
  private final transient SortedSet<Agent> agents;
  private final transient LinkedList<Agent> dead;
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
  @SerializedName("ceny")
  @SkipSerialization
  private DayLog fallBack;

  public Simulation() {
    this.agents = new TreeSet<>(Agent::compareTo);
    this.dead = new LinkedList<>();
  }

  public static int day() {
    return day;
  }

  public static int noClothesPenalty() {
    return noClothesPenalty;
  }

  public void init(Collection<Worker> workers, Collection<Speculator> speculators) {
    Simulation.stock = new Stock(this.stockStrategy, this.fallBack);
    this.agents.addAll(workers);
    this.agents.addAll(speculators);
    noClothesPenalty = this.NO_CLOTHES_PENALTY;
  }

  public void runDay() {
    System.out.println("=== DAY" + day + " ===");
    this.agents.forEach(Agent::act);
    this.agents.forEach(Agent::makeOffers);
    stock.processTransactions();
    this.agents.forEach(Agent::finishDay);

    this.checkForDeaths();
    this.lastDayMaxPrices = stock.log.mapLastMaxPrices();
    this.lastDayMinPrices = stock.log.mapLastMinPrices();
    this.lastDayAvgPrices = stock.log.mapLastAvgPrices();
    this.lastDay = day;

    day++;
    stock.newDay();
  }

  public void checkForDeaths() {
    for (Agent agent : this.agents)
      if (agent.isDead()) {
        this.dead.add(agent);
        System.out.println(agent + " died on day " + day);
      }

    this.dead.forEach(this.agents::remove);
  }

  public int simulationLength() {
    return this.SIMULATION_LENGTH;
  }

  @Override
  public String toString() {
    return "Simulation: day " + day + ". " + this.stockStrategy + "stock";
  }
}
