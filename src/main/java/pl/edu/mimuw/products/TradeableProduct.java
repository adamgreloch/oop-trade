package pl.edu.mimuw.products;

public abstract class TradeableProduct extends Product {

  // TODO make Tradeable as interface
  private final int tradePriority;

  public TradeableProduct(int level, String productName, int tradePriority) {
    super(level, productName);
    this.tradePriority = tradePriority;
  }

  public int tradePriority() {
    return tradePriority;
  }

  @Override
  public String toString() {
    StringBuilder res = new StringBuilder(productName);
    if (level > 0) res.append(" (level ").append(level).append("), ").append(info());
    return res.toString();
  }
}
