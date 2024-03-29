package pl.edu.mimuw.trade.products;

import java.util.Objects;

public abstract class LevelledTradeable extends Tradeable {
  private static int lastId = 0;
  protected final int id;

  public LevelledTradeable(int level, String productName) {
    super(level, productName);
    this.id = assignId();
  }

  private static int assignId() {
    return lastId++;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.productName, this.level, this.id);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || this.getClass() != o.getClass()) return false;
    LevelledTradeable that = (LevelledTradeable) o;
    return this.productName.equals(that.productName) && this.level == that.level && this.id == that.id;
  }

  public abstract int tradePriority();
}
