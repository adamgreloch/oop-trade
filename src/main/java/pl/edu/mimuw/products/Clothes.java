package pl.edu.mimuw.products;

public class Clothes extends ComplexProduct {
  private int daysLeft = 0;

  public Clothes(int level) {
    super(level);
    this.daysLeft = level * level;
  }

  public int wearOnce() {
    return this.daysLeft--;
  }
}
