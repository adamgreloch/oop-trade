package pl.edu.mimuw.trade.agents.career;

import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.Tool;

public class Engineer extends Occupation {
  public Engineer() {
    super("inzynier");
  }

  protected ProductivityVector makeBuff(int buffValue) {
    return (new ProductivityVector()).buffTools(buffValue);
  }

  public Product produceBuffedProduct(int level) {
    return new Tool(level);
  }
}
