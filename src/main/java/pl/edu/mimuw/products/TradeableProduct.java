package pl.edu.mimuw.products;

import java.util.Objects;

public abstract class TradeableProduct {
  protected final String productName;
  protected final int level;
  private final int tradePriority;

  public TradeableProduct(int level, String productName, int tradePriority) {
    this.productName = productName;
    this.level = level;
    this.tradePriority = tradePriority;
  }

  public int level() {
    return level;
  }

  public int tradePriority() {
    return tradePriority;
  }

  /**
   * Note: two TradeableProduct objects are considered equal,
   * iff their product type (clothes, tools, programs etc.) is equal.
   * This means that objects of the same product type are not
   * distinguishable by this definition.
   *
   * @see DistinguishableProduct
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TradeableProduct that = (TradeableProduct) o;
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
