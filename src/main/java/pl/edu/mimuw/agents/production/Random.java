package pl.edu.mimuw.agents.production;

import pl.edu.mimuw.Simulation;
import pl.edu.mimuw.agents.Worker;
import pl.edu.mimuw.agents.productivity.ProductivityVector;
import pl.edu.mimuw.bag.Bag;
import pl.edu.mimuw.products.*;

public class Random implements ProductionStrategy {
  public Random() {
  }

  public void produce(Worker worker, Bag destination) {
    Product[] products = ProductFactory.previewProducts();

    Product buffed = worker.getCareer().currentProduct();
    Product picked = products[Simulation.RANDOM.nextInt(products.length)];

    if (picked.equals(buffed))
      picked = buffed;
    destination.storeProduct(picked, ProductivityVector.find(worker.getProductivity(), picked));
  }
}