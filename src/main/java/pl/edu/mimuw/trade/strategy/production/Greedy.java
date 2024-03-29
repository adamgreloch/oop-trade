package pl.edu.mimuw.trade.strategy.production;

import pl.edu.mimuw.trade.agents.ProductivityVector;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.*;
import pl.edu.mimuw.trade.simulation.Simulation;
import pl.edu.mimuw.trade.strategy.ProductionStrategy;

import static pl.edu.mimuw.trade.simulation.Simulation.stock;

public class Greedy extends ProductionStrategy {
  public Greedy() {
    super("zachlanny");
  }

  public Product pickToProduce(Worker worker) {
    double profit, maxProfit = 0;
    int quantity;
    int today = Simulation.day();
    double avg;
    Product picked = null;
    for (Tradeable product : ProductFactory.previewTradeable()) {
      quantity = ProductivityVector.find(worker.getProductivity(), product);
      avg = stock.log.getAveragePrice(today - 1, product);
      profit = quantity * avg;
      if (profit > maxProfit) {
        maxProfit = profit;
        picked = product;
      }
    }
    return picked;
  }
}
