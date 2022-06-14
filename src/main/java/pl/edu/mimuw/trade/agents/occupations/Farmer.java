package pl.edu.mimuw.trade.agents.occupations;

import pl.edu.mimuw.trade.agents.Occupation;
import pl.edu.mimuw.trade.products.Food;
import pl.edu.mimuw.trade.products.Product;

public class Farmer extends Occupation {
  public Farmer() {
    super("rolnik");
  }

  public Occupation copyOf() {
    return new Farmer();
  }

  public Product produceBuffedProduct(int level) {
    return new Food(1);
  }
}
