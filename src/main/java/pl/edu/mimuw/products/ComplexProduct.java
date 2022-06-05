package pl.edu.mimuw.products;

/**
 * ComplexProduct effects vary based on product's level,
 * thus it is more advanced than primitive products
 * such as Diamonds or Food.
 */

public abstract class ComplexProduct {
  protected int level;

  public ComplexProduct(int level) {
    this.level = level;
  }

  @Override
  public abstract String toString();
}
