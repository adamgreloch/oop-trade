package pl.edu.mimuw.trade.strategy.production;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.Product;

import java.util.Set;

public class AverageProduction extends ProductionStrategy {
  private final int reachPast;

  public AverageProduction(int reachPast) {
    super("sredniak");
    this.reachPast = reachPast;
  }

  public Set<Product> produce(Worker worker) {
    return null;
  }
}
