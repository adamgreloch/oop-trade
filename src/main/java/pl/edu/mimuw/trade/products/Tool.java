package pl.edu.mimuw.trade.products;

import pl.edu.mimuw.trade.agents.productivity.ProductivityBuff;
import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;

public class Tool extends DistinguishableTradeable implements ProductivityBuff {

  public Tool(int level) {
    super(level, "narzedzia");
  }

  @Override
  public ProductivityVector getBuffValue() {
    return new ProductivityVector(level());
  }

  public int tradePriority() {
    return 2;
  }
}
