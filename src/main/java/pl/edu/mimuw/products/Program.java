package pl.edu.mimuw.products;

import pl.edu.mimuw.agents.productivity.ProductivityBuff;
import pl.edu.mimuw.agents.productivity.ProductivityVector;

public class Program extends DistinguishableProduct implements ProductivityBuff {

  public Program(int level) {
    super(level, "program", 3);
  }

  @Override
  public ProductivityVector getBuffValue() {
    return new ProductivityVector();
  }
}
