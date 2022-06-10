package pl.edu.mimuw.trade.adapter;

import com.google.gson.InstanceCreator;
import pl.edu.mimuw.trade.simulation.Simulation;

import java.lang.reflect.Type;

public class SimulationInstanceCreator implements InstanceCreator<Simulation> {
  @Override
  public Simulation createInstance(Type type) {
    return new Simulation();
  }
}
