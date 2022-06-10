package pl.edu.mimuw.trade.strategy.purchase;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.simulation.Offer;

import java.util.Set;

public class Mechanized extends PurchaseStrategy {
  public Mechanized() {
    super("zmechanizowany");
  }

  public Set<Offer> purchasesToOffer(Worker worker) {
    return null;
  }
}
