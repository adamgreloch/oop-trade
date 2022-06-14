package pl.edu.mimuw.trade.agents.occupations;

import pl.edu.mimuw.trade.agents.Occupation;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.Program;

public class Programmer extends Occupation {
  public Programmer() {
    super("programista");
  }

  public Occupation copyOf() {
    return new Programmer();
  }

  public Product produceBuffedProduct(int level) {
    return new Program(level);
  }
}
