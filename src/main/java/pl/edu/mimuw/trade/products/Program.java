package pl.edu.mimuw.trade.products;

import pl.edu.mimuw.trade.agents.productivity.ProductivityBuff;
import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;

public class Program extends DistinguishableTradeable implements ProductivityBuff {

  public Program(int level) {
    super(level, "programy");
  }

  @Override
  public ProductivityVector getBuffValue() {
    return new ProductivityVector();
  }

  public int tradePriority() {
    return 3;
  }
}
