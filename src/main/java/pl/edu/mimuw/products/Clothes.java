package pl.edu.mimuw.products;

import java.util.Objects;

public class Clothes extends DistinguishableProduct {
  private static int lastId = 0;
  private final int id;
  private int daysLeft = 0;

  public Clothes(int level) {
    super(level, "clothes", 1);
    this.daysLeft = level * level;
    this.id = assignId();
  }

  private static int assignId() {
    return lastId++;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Clothes that = (Clothes) o;
    return productName.equals(that.productName) && level == that.level && id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(productName, id);
  }

  public int wearOnce() {
    return --daysLeft;
  }

  @Override
  public String info() {
    return "days left: " + daysLeft;
  }
}
