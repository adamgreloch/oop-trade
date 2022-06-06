package pl.edu.mimuw.products;

import java.util.Objects;

public class DistinguishableProduct extends TradeableProduct {
  private static int lastId = 0;
  private final int id;

  private static int assignId() {
    return lastId++;
  }

  public DistinguishableProduct(int level, String productName, int tradePriority) {
    super(level, productName, tradePriority);
    this.id = assignId();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DistinguishableProduct that = (DistinguishableProduct) o;
    return productName.equals(that.productName) && level == that.level && id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(productName, id);
  }
}
