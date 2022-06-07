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

  public boolean productEquals(Product product) {
    return this.productName.equals(product.productName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productName);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product that = (Product) o;
    return productName.equals(that.productName);
  }

  @Override
  public String toString() {
    return productName;
  }

  public String info() {
    return "";
  }
}
