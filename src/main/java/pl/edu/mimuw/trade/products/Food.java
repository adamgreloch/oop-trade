package pl.edu.mimuw.trade.products;

public class Food extends StackedTradeable {
  public Food(int quantity) {
    super(0, "food", quantity);
  }

  public Food add(int amount) {
    return new Food(quantity + amount);
  }

  public int tradePriority() {
    return 0;
  }
}
