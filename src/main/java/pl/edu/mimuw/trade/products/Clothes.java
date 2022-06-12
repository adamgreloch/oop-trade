package pl.edu.mimuw.trade.products;

import pl.edu.mimuw.trade.agents.productivity.ProductivityBuff;
import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;

public class Clothes extends DistinguishableTradeable implements ProductivityBuff, Levelled {
  public static final int NO_CLOTHES_THRESHOLD = 100;

  private int daysLeft;

  public Clothes(int level) {
    super(level, "ubrania");
    this.daysLeft = level * level;
  }

  public int wearOnce() {
    return --daysLeft;
  }

  @Override
  public String info() {
    return "days left: " + daysLeft;
  }

  @Override
  public ProductivityVector getBuffValue() {
    return new ProductivityVector();
  }

  public int tradePriority() {
    return 1;
  }
}
