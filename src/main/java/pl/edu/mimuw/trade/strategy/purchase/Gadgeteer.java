package pl.edu.mimuw.trade.strategy.purchase;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.stock.Offer;

import java.util.Set;

public class Gadgeteer extends PurchaseStrategy {
  public Gadgeteer() {
    super("gadzeciarz");
  }

  public Set<Offer> purchasesToOffer(Worker worker) {
    return null;
  }
}
