package pl.edu.mimuw.trade.strategy.purchase;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.simulation.Offer;

import java.util.Set;

public class Stickler extends PurchaseStrategy {
  public Stickler() {
    super("czyscioszek");
  }

  public Set<Offer> purchasesToOffer(Worker worker) {
    return null;
  }
}
