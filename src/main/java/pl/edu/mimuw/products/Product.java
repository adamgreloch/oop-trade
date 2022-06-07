package pl.edu.mimuw.products;

import java.util.Objects;

public class Product {
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
   * @ Note: two Product objects are considered equal,
   * iff their product type (clothes, tools, programs etc.) is equal.
   * This means that objects of this class are not
   * distinguishable by this definition, but subclasses can be.
   * @see DistinguishableProduct
   */

  public Product ignoreLevel() {
    return new Product(this.level, this.productName);
  }

  @Override
  public boolean equals(Object o) {
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
    if (level > 0) res.append(" (level ").append(level).append("), ").append(info());
    return res.toString();
  }

  public String info() {
    return "";
  }
}
