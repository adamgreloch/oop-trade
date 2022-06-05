package pl.edu.mimuw.products;

import pl.edu.mimuw.ProductivityModifier;
import pl.edu.mimuw.ProductivityVector;

public class Tool extends ComplexProduct implements ProductivityModifier {

  public Tool(int level) {
    super(level);
  }

  @Override
  public String toString() {
    return null;
  }

  @Override
  public ProductivityVector getModifierValue() {
    return new ProductivityVector(level);
  }
}
