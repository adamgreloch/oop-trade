package pl.edu.mimuw.products;

import pl.edu.mimuw.ProductivityModifier;
import pl.edu.mimuw.ProductivityVector;

public class Tool extends TradeableProduct implements ProductivityModifier {

  public Tool(int level) {
    super(level, "tool");
  }

  @Override
  public String toString() {
    return null;
  }

  @Override
  public ProductivityVector getModifierValue() {
    return new ProductivityVector(level());
  }
}
