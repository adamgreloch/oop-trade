package pl.edu.mimuw.trade.products;

public class Diamond extends Product {
  private final double value;

  public Diamond(double value) {
    super(0, "diamenty");
    this.value = value;
  }

  public double value() {
    return value;
  }

  public Diamond add(double amount) {
    return new Diamond(value + amount);
  }
}
