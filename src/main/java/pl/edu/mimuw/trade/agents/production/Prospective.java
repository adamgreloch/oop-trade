package pl.edu.mimuw.trade.agents.production;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.Product;

import java.util.Set;

public class Prospective implements ProductionStrategy {
  private final int reachPast;

  public Prospective(int reachPast) {
    this.reachPast = reachPast;
  }

  public Set<Product> produce(Worker worker) {
    return null;
  }
}
