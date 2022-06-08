package pl.edu.mimuw.trade.products;

public abstract class Tradeable extends Product {
  public Tradeable(int level, String productName) {
    super(level, productName);
  }

  public abstract int tradePriority();
}
