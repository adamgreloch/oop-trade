package pl.edu.mimuw.agents.career;

import pl.edu.mimuw.agents.productivity.ProductivityVector;
import pl.edu.mimuw.products.Food;
import pl.edu.mimuw.products.Product;

public class Farmer extends Occupation {
  public Farmer() {
    super("farmer");
  }

  protected ProductivityVector makeBuff(int buffValue) {
    return (new ProductivityVector()).buffFood(buffValue);
  }

  public Product produceBuffedProduct(int level) {
    return new Food();
  }
}
