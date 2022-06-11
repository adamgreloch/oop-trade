package pl.edu.mimuw.trade.strategy.production;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;
import pl.edu.mimuw.trade.products.Diamond;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.simulation.Simulation;

public class Random extends ProductionStrategy {
  public Random() {
    super("losowy");
  }

  public Product pickToProduce(Worker worker) {
    Product[] products = ProductFactory.previewProducts();

    Product buffed = worker.getCareer().currentProduct();
    Product picked = products[Simulation.RANDOM.nextInt(products.length)];

    if (picked.equals(buffed))
      picked = buffed;

    int quantity = ProductivityVector.find(worker.getProductivity(), picked);

    if (picked instanceof Diamond) {
      worker.earnDiamonds(quantity);
      return null;
    }
    return picked;
  }
}
