package pl.edu.mimuw.products;

public class Clothes extends DistinguishableProduct {
  private int daysLeft = 0;

  public Clothes(int level) {
    super(level, "clothes");
    this.daysLeft = level * level;
  }

  public String toString() {
    return null;
  }

  public int wearOnce() {
    return --this.daysLeft;
  }
}
