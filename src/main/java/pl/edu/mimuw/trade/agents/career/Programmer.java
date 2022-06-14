package pl.edu.mimuw.trade.agents.career;

import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.Program;

public class Programmer extends Occupation {
  public Programmer() {
    super("programista");
  }

  public Product produceBuffedProduct(int level) {
    return new Program(level);
  }
}
