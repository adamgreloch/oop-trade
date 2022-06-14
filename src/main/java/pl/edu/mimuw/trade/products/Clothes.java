package pl.edu.mimuw.trade.products;

public class Clothes extends LevelledTradeable {
  public static final int NO_CLOTHES_THRESHOLD = 100;

  private int daysLeft;

  public Clothes(int level) {
    super(level, "ubrania");
    this.daysLeft = level * level;
  }

  public int wearOnce() {
    return --this.daysLeft;
  }

  @Override
  public String info() {
    return "days left: " + this.daysLeft;
  }

  public int tradePriority() {
    return 1;
  }
}
