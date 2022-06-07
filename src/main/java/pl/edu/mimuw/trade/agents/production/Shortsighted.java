package pl.edu.mimuw.trade.agents.production;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;
import pl.edu.mimuw.trade.bag.Bag;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.products.TradeableProduct;
import pl.edu.mimuw.trade.stock.Simulation;
import pl.edu.mimuw.trade.stock.Stock;

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
