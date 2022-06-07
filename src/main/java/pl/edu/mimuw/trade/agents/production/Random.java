package pl.edu.mimuw.trade.agents.production;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;
import pl.edu.mimuw.trade.bag.Bag;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.stock.Simulation;

public class Random implements ProductionStrategy {
  public Random() {
  }

  public void produce(Worker worker, Bag destination) {
    Product[] products = ProductFactory.previewProducts();

    Product buffed = worker.getCareer().currentProduct();
    Product picked = products[Simulation.RANDOM.nextInt(products.length)];

    if (picked.equals(buffed))
      picked = buffed;
    destination.storeNewProducts(picked, ProductivityVector.find(worker.getProductivity(), picked));
  }
}
