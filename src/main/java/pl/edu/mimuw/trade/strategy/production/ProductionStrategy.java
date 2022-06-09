package pl.edu.mimuw.trade.strategy.production;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.strategy.Strategy;

import java.util.Set;

/**
 * Interfejs strategii produkcji Robotnika.
 */
public abstract class ProductionStrategy extends Strategy {

  public ProductionStrategy(String name) {
    super(name);
  }

  public abstract Set<Product> produce(Worker worker);
}
