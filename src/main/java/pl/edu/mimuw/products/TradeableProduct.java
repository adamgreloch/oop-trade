package pl.edu.mimuw.products;

public abstract class TradeableProduct extends Product {
  private final int tradePriority;

  public TradeableProduct(int level, String productName, int tradePriority) {
    super(level, productName);
    this.tradePriority = tradePriority;
  }

  public int tradePriority() {
    return tradePriority;
  }
}
