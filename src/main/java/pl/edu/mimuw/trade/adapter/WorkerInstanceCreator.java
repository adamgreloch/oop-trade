package pl.edu.mimuw.trade.adapter;

import com.google.gson.InstanceCreator;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.simulation.Stock;

import java.lang.reflect.Type;

public class WorkerInstanceCreator implements InstanceCreator<Worker> {
  private Stock stock;

  public WorkerInstanceCreator(Stock stock) {
    this.stock = stock;
  }

  @Override
  public Worker createInstance(Type type) {
    return new Worker();
  }
}
