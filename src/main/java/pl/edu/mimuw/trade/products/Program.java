package pl.edu.mimuw.trade.products;

import pl.edu.mimuw.trade.agents.productivity.ProductivityBuff;
import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;

public class Program extends DistinguishableProduct implements ProductivityBuff {

  public Program(int level) {
    super(level, "program", 3);
  }

  @Override
  public ProductivityVector getBuffValue() {
    return new ProductivityVector();
  }
}
