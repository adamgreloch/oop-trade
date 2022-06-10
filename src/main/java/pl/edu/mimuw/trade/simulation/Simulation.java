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

  @Expose(serialize = true, deserialize = false)
  private static int day = 1;
  @SerializedName("gielda")
  private final StockStrategy stockStrategy;
  @SerializedName("dlugosc")
  @Expose(serialize = false)
  private final int SIMULATION_LENGTH = 5;
  @SerializedName("kara_za_brak_ubran")
  private final int NO_CLOTHES_PENALTY = 2;

  private transient final LinkedList<Agent> agents;
  private transient final LinkedList<Worker> workers;
  private transient final LinkedList<Speculator> speculators;
  private transient final LinkedList<Agent> dead;

  private transient final Stock stock;
  private transient final StockLog log;

  @SerializedName("ceny_srednie")
  private Map<String, Double> lastDayAvgPrices;
  @SerializedName("ceny_max")
  private Map<String, Double> lastDayMaxPrices;
  @SerializedName("ceny_min")
  private Map<String, Double> lastDayMinPrices;
  @SerializedName("ceny")
  @Expose(serialize = false)
  private DayLog fallBack;

  public Simulation(StockStrategy stockStrategy, LinkedList<Worker> workers, LinkedList<Speculator> speculators) {
    this.agents = new LinkedList<>();
    this.workers = workers;
    this.speculators = speculators;

    this.stockStrategy = stockStrategy;
    this.dead = new LinkedList<>();
    this.log = new StockLog();
    this.stock = new Stock(stockStrategy, this.log);
  }

  public static int day() {
    return day;
  }

  public void runDay() {
    if (day == 1) {
      this.agents.addAll(workers);
      this.agents.addAll(speculators);
    }

    agents.forEach(Agent::act);

    agents.forEach(Agent::makeOffers);
    stock.processTransactions();

    agents.forEach(Agent::finishDay);

    checkForDeaths();
    lastDayMaxPrices = log.mapLastMaxPrices();
    lastDayMinPrices = log.mapLastMinPrices();
    lastDayAvgPrices = log.mapLastAvgPrices();
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
    return log.getCurrent();
  }
}
