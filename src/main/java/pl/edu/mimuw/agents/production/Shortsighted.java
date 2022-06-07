package pl.edu.mimuw.agents.production;

import pl.edu.mimuw.agents.Worker;
import pl.edu.mimuw.agents.productivity.ProductivityVector;
import pl.edu.mimuw.bag.Bag;
import pl.edu.mimuw.products.ProductFactory;
import pl.edu.mimuw.products.TradeableProduct;
import pl.edu.mimuw.stock.Simulation;
import pl.edu.mimuw.stock.Stock;

public class Shortsighted implements ProductionStrategy {
  private final Stock stock;

  public Shortsighted(Stock stock) {
    this.stock = stock;
  }

  public void produce(Worker worker, Bag destination) {
    TradeableProduct picked = findBest();
    destination.storeNewProducts(picked, ProductivityVector.find(worker.getProductivity(), picked));
  }

  private TradeableProduct findBest() {
    double maxAvg = 0, avg;
    int yesterday = Math.max(Simulation.day() - 1, 0);
    TradeableProduct pick = null;
    for (TradeableProduct product : ProductFactory.previewTradeable()) {
      avg = stock.getAveragePrice(yesterday, product);
      if (avg > maxAvg) {
        pick = product;
        maxAvg = avg;
      }
    }
    return pick;
  }
}
