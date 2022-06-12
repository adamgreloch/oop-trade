package pl.edu.mimuw.trade.products;

public class Diamond extends Product {
  private final double value;

  public Diamond(double value) {
    super(0, "diamenty");
    this.value = value;
  }

  public double value() {
    return this.value;
  }
}
