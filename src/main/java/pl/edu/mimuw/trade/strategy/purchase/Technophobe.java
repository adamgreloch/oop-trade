package pl.edu.mimuw.trade.strategy.purchase;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.simulation.Offer;
import pl.edu.mimuw.trade.simulation.OfferFactory;

import java.util.Collections;
import java.util.Set;

public class Technophobe extends PurchaseStrategy {
  private final static int FOOD_QUANTITY = 100;

  public Technophobe() {
    super("technofob");
  }

  public Set<Offer> purchasesToOffer(Worker worker) {
    Offer buyFood = OfferFactory.workerPurchaseOffer(worker, ProductFactory.food, FOOD_QUANTITY);
    return Collections.singleton(buyFood);
  }
}
