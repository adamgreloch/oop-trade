package pl.edu.mimuw.trade.simulation;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.GsonWrapper;
import pl.edu.mimuw.trade.agents.Speculator;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.strategy.stock.StockStrategy;

import java.util.LinkedList;

public class SimulationWrapper {
  @SerializedName("info")
  private Simulation simulation;
  @SerializedName("robotnicy")
  private final LinkedList<Worker> workers;
  @SerializedName("spekulanci")
  private final LinkedList<Speculator> speculators;

  private transient DayLog current; // TODO do outputu

  public SimulationWrapper(StockStrategy stockStrategy) {
    this.workers = new LinkedList<>();
    this.speculators = new LinkedList<>();
    this.simulation = new Simulation(stockStrategy);
  }

  public void runSimulation() {
    simulation.init(workers, speculators);
    while (Simulation.day() <= simulation.simulationLength()) {
      simulation.runDay();
      current = simulation.getCurrent();
      System.out.println(GsonWrapper.toJson(this));
    }
  }
}
