package pl.edu.mimuw.agents.purchase;

import pl.edu.mimuw.agents.Worker;
import pl.edu.mimuw.products.Food;
import pl.edu.mimuw.stock.Offer;

import java.util.Collections;
import java.util.Set;

public class Technophobe implements PurchaseStrategy {
  private final static int FOOD_QUANTITY = 100;

  public Technophobe() {
  }

  public Set<Offer> purchasesToOffer(Worker worker) {
    Offer buyFood = new Offer(worker, new Food(), FOOD_QUANTITY, true);
    return Collections.singleton(buyFood);
  }
}
