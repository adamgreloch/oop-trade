package pl.edu.mimuw.products;

import java.util.Objects;

public abstract class TradeableProduct {
  protected final String productName;
  protected final int level;

  public TradeableProduct(int level, String productName) {
    this.productName = productName;
    this.level = level;
  }

  public int level() {
    return level;
  }

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
