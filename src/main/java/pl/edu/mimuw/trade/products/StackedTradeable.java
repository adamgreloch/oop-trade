package pl.edu.mimuw.trade.products;

import java.util.Objects;

public abstract class StackedTradeable extends Tradeable {
  protected final int quantity;

  public StackedTradeable(int level, String productName, int quantity) {
    super(level, productName);
    this.quantity = quantity;
  }

  public int quantity() {
    return quantity;
  }

  @Override
  public int hashCode() {
    return Objects.hash(productName, quantity);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StackedTradeable that = (StackedTradeable) o;
    return productName.equals(that.productName);
  }
}
