package pl.edu.mimuw.stock;

import pl.edu.mimuw.Simulation;
import pl.edu.mimuw.products.*;

import java.util.LinkedList;
import java.util.List;

public class Log {

  private final List<DayLog> days;
  private final DayLog fallBack;
  private DayLog current;

  public Log() {
    this.days = new LinkedList<>();
    this.current = new DayLog(1);
    this.fallBack = new DayLog(0);
  }

  /**
   * @return Price of the lowest product purchase price the day before or price from zero-day.
   */
  public double previousLowest(TradeableProduct product) {
    return 0;
  }

  public void setFallBackPrices(double food, double clothes, double tools, double programs) {
    fallBack.log(new Food(), food, 1);
    fallBack.log(new Clothes(1), clothes, 1);
    fallBack.log(new Tool(1), tools, 1);
    fallBack.log(new Program(1), programs, 1);
  }

  public void log(TradeableProduct product, double sellPrice, int soldQuantity) {
    if (current.day < Simulation.day()) {
      days.add(current);
      current.fallBack(fallBack);
      current = new DayLog(Simulation.day());
    }
    current.log(product, sellPrice, soldQuantity);
  }
}
