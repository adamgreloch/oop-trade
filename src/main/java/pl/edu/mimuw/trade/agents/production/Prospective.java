package pl.edu.mimuw.trade.agents.production;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.bag.Bag;

public class Prospective implements ProductionStrategy {
  private final int reachPast;

  public Prospective(int reachPast) {
    this.reachPast = reachPast;
  }

  public void produce(Worker worker, Bag destination) {
  }
}
