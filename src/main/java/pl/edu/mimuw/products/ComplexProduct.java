package pl.edu.mimuw.products;

import pl.edu.mimuw.Productivity;
import pl.edu.mimuw.ProductivityModifier;

/**
 * ComplexProduct effects vary based on product's level,
 * thus it is more advanced than primitive products
 * such as Diamonds or Food.
 */

public abstract class ComplexProduct implements ProductivityModifier {
  protected int level = 1;

  @Override
  public String toString() {
    return "product";
  }

  public abstract void apply(Productivity productivity);
}
