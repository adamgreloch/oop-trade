package pl.edu.mimuw.trade.strategy.production;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.products.Tradeable;
import pl.edu.mimuw.trade.simulation.Simulation;

public class Shortsighted extends ProductionStrategy {

  public Shortsighted() {
    super("krotkowzroczny");
  }

  public Product pickToProduce(Worker worker) {
    double maxAvg = 0, avg;
    int yesterday = Math.max(Simulation.day() - 1, 0);
    Product picked = null;
    for (Tradeable product : ProductFactory.previewTradeable()) {
      avg = Simulation.stock.getAveragePrice(yesterday, product);
      if (avg > maxAvg) {
        picked = product;
        maxAvg = avg;
      }
    }
    return picked;
  }
}
