package pl.edu.mimuw.products;

import java.util.Objects;

public class DistinguishableProduct extends TradeableProduct {

  public DistinguishableProduct(int level, String productName) {
    super(level, productName);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DistinguishableProduct that = (DistinguishableProduct) o;
    return productName.equals(that.productName) && level == that.level;
  }

  @Override
  public int hashCode() {
    return Objects.hash(productName, level);
  }
}
