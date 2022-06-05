package pl.edu.mimuw.products;

import pl.edu.mimuw.ProductivityModifier;

/**
 * ComplexProduct effects vary based on product's level,
 * thus it is more advanced than primitive products
 * such as Diamonds or Food.
 */

public abstract class ComplexProduct {
  private int level;

  public ComplexProduct(int level) {
    this.level = level;
  }

  @Override
  public String toString() {
    return "product";
  }
}
