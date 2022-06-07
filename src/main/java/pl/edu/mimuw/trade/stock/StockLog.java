package pl.edu.mimuw.trade.stock;

import pl.edu.mimuw.trade.products.*;

import java.util.LinkedList;
import java.util.List;

public class StockLog {

  private final List<DayLog> days;
  private final DayLog fallBack;
  private DayLog previous;
  private DayLog current;

  public StockLog() {
    this.days = new LinkedList<>();
    this.current = new DayLog(1);
    this.fallBack = new DayLog(0);
    this.previous = fallBack;
  }

  /**
   * @return Price of the lowest product purchase price the day before or price from zero-day.
   */
  public double previousLowest(TradeableProduct product) {
    return previous.min(product, fallBack);
  }

  public void setFallBackPrices(double food, double clothes, double tools, double programs) {
    fallBack.log(new Food(), food, 1);
    fallBack.log(new Clothes(1), clothes, 1);
    fallBack.log(new Tool(1), tools, 1);
    fallBack.log(new Program(1), programs, 1);
  }

  public void log(TradeableProduct product, double sellPrice, int soldQuantity) {
    current.log(product, sellPrice, soldQuantity);
  }

  void newDay() {
    if (current.day < Simulation.day()) {
      days.add(current);
      previous = current;
      current = new DayLog(Simulation.day());
    }
  }

  public double getAveragePrice(int day, TradeableProduct product) {
    if (days.isEmpty() || day == 0 || !days.get(day - 1).soldThatDay(product)) return fallBack.getAveragePrice(product);
    return days.get(day - 1).getAveragePrice(product);
  }

  public int getSoldQuantity(int day, TradeableProduct product) {
    if (days.isEmpty() || day == 0 || !days.get(day - 1).soldThatDay(product)) return fallBack.getSoldQuantity(product);
    return days.get(day - 1).getSoldQuantity(product);
  }

  public String printCurrent() {
    return current.toString();
  }
}
