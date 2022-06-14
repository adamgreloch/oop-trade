package pl.edu.mimuw.trade.agents.career;

import pl.edu.mimuw.trade.products.Food;
import pl.edu.mimuw.trade.products.Product;

public class Farmer extends Occupation {
  public Farmer() {
    super("rolnik");
  }

  public Product produceBuffedProduct(int level) {
    return new Food(1);
  }
}
