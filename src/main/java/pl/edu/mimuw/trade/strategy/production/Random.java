package pl.edu.mimuw.trade.strategy.production;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.stock.Simulation;

import java.util.Set;

public class Random extends ProductionStrategy {
  public Random() {
    super("losowy");
  }

  public Set<Product> produce(Worker worker) {
    Product[] products = ProductFactory.previewProducts();

    Product buffed = worker.getCareer().currentProduct();
    Product picked = products[Simulation.RANDOM.nextInt(products.length)];

    if (picked.equals(buffed))
      picked = buffed;
    int quantity = ProductivityVector.find(worker.getProductivity(), picked);
    return ProductFactory.produceAlike(picked, quantity, worker.productionLevel(picked));
  }
}
