package pl.edu.mimuw.trade.strategy.purchase;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.simulation.Offer;
import pl.edu.mimuw.trade.strategy.Strategy;

import java.util.Set;

/**
 * Interfejs strategii konsumpcji, czyli wskazywanie, ile czego Robotnik ma kupować i w jaki sposób ma
 * stosować programy komputerowe.
 */
public abstract class PurchaseStrategy extends Strategy {

  public PurchaseStrategy(String name) {
    super(name);
  }

  public abstract Set<Offer> purchasesToOffer(Worker worker);

}
