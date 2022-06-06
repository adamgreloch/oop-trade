package pl.edu.mimuw.stock;

import pl.edu.mimuw.products.TradeableProduct;

public class Log {

  public Log() {
  }

  /**
   * @return Price of the lowest product purchase price the day before or price from zero-day.
   */
  public double previousLowest(TradeableProduct product) {
    return 0;
  }

  public void logPrice(TradeableProduct product, double sellPrice) {
    System.out.println(product + " " + sellPrice);
  }
}
