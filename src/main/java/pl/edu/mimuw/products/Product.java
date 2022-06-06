package pl.edu.mimuw.products;

import java.util.Objects;

public abstract class Product {
  protected final String productName;
  protected final int level;

  public Product(int level, String productName) {
    this.productName = productName;
    this.level = level;
  }

  public int level() {
    return level;
  }

  /**
   * Note: two Product objects are considered type equal,
   * iff their product type (clothes, tools, programs etc.) is equal.
   * This means that objects of the same product type are not
   * distinguishable by this definition.
   *
   * @see DistinguishableProduct
   */
  public boolean typeEquals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product that = (Product) o;
    return productName.equals(that.productName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productName);
  }

  @Override
  public String toString() {
    StringBuilder res = new StringBuilder(productName);
    if (level > 0) res.append("(level ").append(level).append(")");
    return res.toString();
  }
}
