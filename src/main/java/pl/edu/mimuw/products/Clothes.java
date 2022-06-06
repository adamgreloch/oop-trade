package pl.edu.mimuw.products;

import java.util.Objects;

public class Clothes extends DistinguishableProduct {
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
}
