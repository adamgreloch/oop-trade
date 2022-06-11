package pl.edu.mimuw.trade.strategy.production;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.products.Tradeable;
import pl.edu.mimuw.trade.simulation.Simulation;

public class Prospective extends ProductionStrategy {
  @SerializedName("historia_perspektywy")
  private int reachPast;

  public Prospective() {
    super("perspektywiczny");
  }

  public Product pickToProduce(Worker worker) {
    int today = Simulation.day();
    double diffMax = 0, diff;
    Product picked = null;
    for (Tradeable product : ProductFactory.previewTradeable()) {
      diff = Simulation.stock.getAveragePrice(today, product)
              - Simulation.stock.getAveragePrice(today - reachPast, product);
      if (diff > diffMax) {
        picked = product;
        diffMax = diff;
      }
    }
    return picked;
  }
}
