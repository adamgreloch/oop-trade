package pl.edu.mimuw.agents.career;

import pl.edu.mimuw.agents.productivity.ProductivityVector;
import pl.edu.mimuw.products.Product;
import pl.edu.mimuw.products.Tool;

public class Engineer extends Occupation {
  public Engineer() {
    super("engineer");
  }

  protected ProductivityVector makeBuff(int buffValue) {
    return (new ProductivityVector()).buffTools(buffValue);
  }

  public Product produceBuffedProduct(int level) {
    return new Tool(level);
  }
}
