package pl.edu.mimuw.trade.products;

public class Food extends StackedTradeable {
  public static final int MINOR_STARVATION_PENALTY = -100;
  public static final int MAJOR_STARVATION_PENALTY = -300;

  public Food(int quantity) {
    super(0, "jedzenie", quantity);
  }

  public Food add(int amount) {
    return new Food(quantity + amount);
  }

  public int tradePriority() {
    return 0;
  }
}
