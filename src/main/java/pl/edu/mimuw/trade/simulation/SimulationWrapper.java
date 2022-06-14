package pl.edu.mimuw.trade.simulation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.agents.Speculator;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.io.GsonWrapper;

import java.util.LinkedList;

public class SimulationWrapper {
  @SerializedName("robotnicy")
  private final LinkedList<Worker> workers;
  @SerializedName("spekulanci")
  private final LinkedList<Speculator> speculators;
  private transient final StringBuffer outputBuffer;
  private final transient JsonObject output;
  private final transient JsonArray dailyOutputs;
  @SerializedName("info")
  private Simulation simulation;

  public SimulationWrapper() {
    this.workers = new LinkedList<>();
    this.speculators = new LinkedList<>();
    this.outputBuffer = new StringBuffer();
    this.output = new JsonObject();
    this.dailyOutputs = new JsonArray();
    this.output.add("symulacja", this.dailyOutputs);
  }

  public void runSimulation() {
    this.simulation.init(this.workers, this.speculators);
    while (Simulation.day() <= this.simulation.simulationLength()) {
      System.out.println("=== DAY " + Simulation.day() + " ===");
      this.simulation.runDay();
      this.dailyOutputs.add(GsonWrapper.toJsonTree(this));
    }
  }

  public String getOutput() {
    this.outputBuffer.append(GsonWrapper.toJson(this.output));
    return this.outputBuffer.toString();
  }
}
