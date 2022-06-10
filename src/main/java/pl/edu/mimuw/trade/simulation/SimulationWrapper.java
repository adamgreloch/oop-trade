package pl.edu.mimuw.trade.simulation;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.GsonWrapper;
import pl.edu.mimuw.trade.agents.Speculator;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.strategy.stock.StockStrategy;

import java.util.LinkedList;
import java.util.List;

public class SimulationWrapper {
  @SerializedName("info")
  private final Simulation simulation;
  @SerializedName("robotnicy")
  private final LinkedList<Worker> workers;
  @SerializedName("spekulanci")
  private final LinkedList<Speculator> speculators;

  public SimulationWrapper(StockStrategy stockStrategy) {
    this.workers = new LinkedList<>();
    this.speculators = new LinkedList<>();
    this.simulation = new Simulation(stockStrategy, workers, speculators);
  }

  public void addWorkers(Worker... workers) {
    this.workers.addAll(List.of(workers));
  }

  public void addSpeculators(Speculator... speculators) {
    this.speculators.addAll(List.of(speculators));
  }

  public Stock stock() {
    return simulation.stock();
  }

  public void runSimulation() {
    simulation.addWorkers(workers);
    simulation.addSpeculators(speculators);
    simulation.run();
    System.out.println(GsonWrapper.toJson(simulation));
  }
}
