package pl.edu.mimuw.trade.agents.production;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.Product;

import java.util.Set;

/**
 * Interfejs strategii produkcji Robotnika.
 */
public interface ProductionStrategy {

  Set<Product> produce(Worker worker);
}
