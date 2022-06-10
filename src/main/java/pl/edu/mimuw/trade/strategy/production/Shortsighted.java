package pl.edu.mimuw.trade.strategy.production;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.products.Tradeable;
import pl.edu.mimuw.trade.simulation.Simulation;
import pl.edu.mimuw.trade.simulation.Stock;

import java.util.Set;

public class Shortsighted extends ProductionStrategy {
  private transient final Stock stock;

  public Shortsighted(Stock stock) {
    super("krotkowzroczny");
    this.stock = stock;
  }

  public Set<Product> produce(Worker worker) {
    Tradeable picked = findBest();
    int quantity = ProductivityVector.find(worker.getProductivity(), picked);
    return ProductFactory.produceAlike(picked, quantity, worker.productionLevel(picked));
  }

  private Tradeable findBest() {
    double maxAvg = 0, avg;
    int yesterday = Math.max(Simulation.day() - 1, 0);
    Tradeable pick = null;
    for (Tradeable product : ProductFactory.previewTradeable()) {
      avg = stock.getAveragePrice(yesterday, product);
      if (avg > maxAvg) {
        pick = product;
        maxAvg = avg;
      }
    }
    return pick;
  }
}
