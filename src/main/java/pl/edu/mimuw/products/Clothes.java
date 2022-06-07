package pl.edu.mimuw.products;

import pl.edu.mimuw.agents.productivity.ProductivityBuff;
import pl.edu.mimuw.agents.productivity.ProductivityVector;

public class Clothes extends DistinguishableProduct implements ProductivityBuff {
  private int daysLeft = 0;

  public Clothes(int level) {
    super(level, "clothes", 1);
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
}
