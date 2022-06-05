package pl.edu.mimuw.strategy;

import pl.edu.mimuw.agents.Worker;
import pl.edu.mimuw.products.Bag;

/**
 * Interfejs strategii produkcji Robotnika.
 */
public interface ProductionStrategy {

  void produce(Worker worker, Bag storage);
}
