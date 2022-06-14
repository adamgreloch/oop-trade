package pl.edu.mimuw.trade.strategy.production;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.*;
import pl.edu.mimuw.trade.simulation.Simulation;
import pl.edu.mimuw.trade.strategy.ProductionStrategy;

import static pl.edu.mimuw.trade.simulation.Simulation.stock;

public class Shortsighted extends ProductionStrategy {

  public Shortsighted() {
    super("krotkowzroczny");
  }

  public Product pickToProduce(Worker worker) {
    double maxAvg = 0, avg;
    int yesterday = Math.max(Simulation.day() - 1, 0);
    Product picked = null;
    for (Tradeable product : ProductFactory.previewTradeable()) {
      avg = stock.log.getAveragePrice(yesterday, product);
      if (avg > maxAvg) {
        picked = product;
        maxAvg = avg;
      }
    }
    return picked;
  }
}
