package pl.edu.mimuw.trade.strategy.production;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.*;
import pl.edu.mimuw.trade.simulation.Simulation;
import pl.edu.mimuw.trade.strategy.ProductionStrategy;

import static pl.edu.mimuw.trade.simulation.Simulation.stock;

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
      diff = stock.log.getAveragePrice(today, product)
              - stock.log.getAveragePrice(today - this.reachPast, product);
      if (diff > diffMax) {
        picked = product;
        diffMax = diff;
      }
    }
    return picked;
  }
}
