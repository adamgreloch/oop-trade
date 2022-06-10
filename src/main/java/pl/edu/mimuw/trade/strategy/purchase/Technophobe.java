package pl.edu.mimuw.trade.strategy.purchase;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.Food;
import pl.edu.mimuw.trade.simulation.Offer;

import java.util.Collections;
import java.util.Set;

public class Technophobe extends PurchaseStrategy {
  private final static int FOOD_QUANTITY = 100;

  public Technophobe() {
    super("technofob");
  }

  public Set<Offer> purchasesToOffer(Worker worker) {
    Offer buyFood = new Offer(worker, new Food(1), FOOD_QUANTITY, true);
    return Collections.singleton(buyFood);
  }
}
