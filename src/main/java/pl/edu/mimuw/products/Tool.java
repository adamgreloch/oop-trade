package pl.edu.mimuw.products;

import pl.edu.mimuw.agents.productivity.ProductivityBuff;
import pl.edu.mimuw.agents.productivity.ProductivityVector;

public class Tool extends DistinguishableProduct implements ProductivityBuff {

  public Tool(int level) {
    super(level, "tool");
  }

  @Override
  public String toString() {
    return null;
  }

  @Override
  public ProductivityVector getBuffValue() {
    return new ProductivityVector(level());
  }
}
