package pl.edu.mimuw.trade.agents.production;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.bag.Bag;

/**
 * Interfejs strategii produkcji Robotnika.
 */
public interface ProductionStrategy {

  void produce(Worker worker, Bag destination);
}
