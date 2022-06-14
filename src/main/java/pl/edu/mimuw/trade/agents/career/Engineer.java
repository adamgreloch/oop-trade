package pl.edu.mimuw.trade.agents.career;

import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.Tool;

public class Engineer extends Occupation {
  public Engineer() {
    super("inzynier");
  }

  public Product produceBuffedProduct(int level) {
    return new Tool(level);
  }
}
