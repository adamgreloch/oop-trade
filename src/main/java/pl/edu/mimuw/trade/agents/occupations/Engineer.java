package pl.edu.mimuw.trade.agents.occupations;

import pl.edu.mimuw.trade.agents.Occupation;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.Tool;

public class Engineer extends Occupation {
  public Engineer() {
    super("inzynier");
  }

  public Occupation copyOf() {
    return new Engineer();
  }

  public Product produceBuffedProduct(int level) {
    return new Tool(level);
  }
}
