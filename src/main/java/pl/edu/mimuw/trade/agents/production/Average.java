package pl.edu.mimuw.trade.agents.production;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.bag.Bag;

public class Average implements ProductionStrategy {
  private final int reachPast;

  public Average(int reachPast) {
    this.reachPast = reachPast;
  }

  public void produce(Worker worker, Bag destination) {
  }
}
