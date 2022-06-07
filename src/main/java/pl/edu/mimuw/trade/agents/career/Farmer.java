package pl.edu.mimuw.trade.agents.career;

import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;
import pl.edu.mimuw.trade.products.Food;
import pl.edu.mimuw.trade.products.Product;

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
