package pl.edu.mimuw.trade.strategy;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.Product;

/**
 * Interfejs strategii produkcji Robotnika.
 */
public abstract class ProductionStrategy extends Strategy {

  public ProductionStrategy(String name) {
    super(name);
  }

  public abstract Product pickToProduce(Worker worker);
}
