package pl.edu.mimuw.products;

import pl.edu.mimuw.ProductivityBuff;
import pl.edu.mimuw.ProductivityVector;

public class Tool extends TradeableProduct implements ProductivityBuff {

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
