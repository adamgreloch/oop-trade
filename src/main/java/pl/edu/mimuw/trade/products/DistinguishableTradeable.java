package pl.edu.mimuw.trade.products;

import java.util.Objects;

public abstract class DistinguishableTradeable extends Tradeable {
  private static int lastId = 0;
  private final int id;

  public DistinguishableTradeable(int level, String productName) {
    super(level, productName);
    this.id = assignId();
  }

  private static int assignId() {
    return lastId++;
  }

  @Override
  public int hashCode() {
    return Objects.hash(productName, level, id);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DistinguishableTradeable that = (DistinguishableTradeable) o;
    return productName.equals(that.productName) && level == that.level && id == that.id;
  }

  public abstract int tradePriority();
}
