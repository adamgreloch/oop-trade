package pl.edu.mimuw.trade.products;

import java.util.Objects;

public class Product {
  protected final String productName;
  protected final int level;

  public Product(int level, String productName) {
    this.productName = productName;
    this.level = level;
  }

  public int level() {
    return this.level;
  }

  /**
   * @ Note: two Product objects are considered equal,
   * iff their product type (clothes, tools, programs etc.) is equal.
   * This means that objects of this class are not
   * distinguishable by this definition, but subclasses can be.
   * @see DistinguishableTradeable
   */

  public Product generalize() {
    return new Product(this.level, this.productName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.productName);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || this.getClass() != o.getClass()) return false;
    Product that = (Product) o;
    return this.productName.equals(that.productName);
  }

  @Override
  public String toString() {
    StringBuilder res = new StringBuilder(this.productName);
    if (this.level > 0) res.append(" (level ").append(this.level).append("), ").append(this.info());
    return res.toString();
  }

  public String productName() {
    return this.productName;
  }

  public String info() {
    return "";
  }
}
