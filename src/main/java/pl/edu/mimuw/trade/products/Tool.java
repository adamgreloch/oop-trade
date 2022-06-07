package pl.edu.mimuw.trade.products;

import pl.edu.mimuw.trade.agents.productivity.ProductivityBuff;
import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;

public class Tool extends DistinguishableProduct implements ProductivityBuff {

  public Tool(int level) {
    super(level, "tool", 2);
  }

  @Override
  public ProductivityVector getBuffValue() {
    return new ProductivityVector(level());
  }
}
