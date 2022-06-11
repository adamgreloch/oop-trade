package pl.edu.mimuw.trade.simulation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.agents.Speculator;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.io.GsonWrapper;

import java.util.LinkedList;

public class SimulationWrapper {
  @SerializedName("info")
  private Simulation simulation;
  @SerializedName("robotnicy")
  private final LinkedList<Worker> workers;
  @SerializedName("spekulanci")
  private final LinkedList<Speculator> speculators;

  private transient final StringBuffer outputBuffer;
  private transient JsonObject output;
  private transient JsonArray dailyOutputs;

  public SimulationWrapper() {
    this.workers = new LinkedList<>();
    this.speculators = new LinkedList<>();
    this.outputBuffer = new StringBuffer();
    this.output = new JsonObject();
    this.dailyOutputs = new JsonArray();
    this.output.add("symulacja", dailyOutputs);
  }

  public void runSimulation() {
    simulation.init(workers, speculators);
    while (Simulation.day() <= simulation.simulationLength()) {
      simulation.runDay();
      dailyOutputs.add(GsonWrapper.toJsonTree(this));
    }
  }

  public String getOutput() {
    outputBuffer.append(GsonWrapper.toJson(output));
    return outputBuffer.toString();
  }
}
