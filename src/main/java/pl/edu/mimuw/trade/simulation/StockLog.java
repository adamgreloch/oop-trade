package pl.edu.mimuw.trade.simulation;

import pl.edu.mimuw.trade.products.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StockLog {

  private final List<DayLog> days;
  private transient final DayLog fallBack;
  private transient DayLog previous;
  private transient DayLog current;

  public StockLog() {
    this.days = new LinkedList<>();
    this.fallBack = new DayLog(0, null);
    this.current = new DayLog(1, this.fallBack);
    this.previous = fallBack;
  }

  public StockLog(DayLog fallBack) {
    this.days = new LinkedList<>();
    this.fallBack = fallBack;
    this.current = new DayLog(1, this.fallBack);
    this.previous = fallBack;
  }

  /**
   * @return Price of the lowest product purchase price the day before or price from zero-day.
   */
  public double previousLowest(Tradeable product) {
    return previous.min(product, fallBack);
  }

  public void setFallBackPrices(double food, double clothes, double tools, double programs) {
    fallBack.logTransaction(new Food(1), food, 1);
    fallBack.logTransaction(new Clothes(1), clothes, 1);
    fallBack.logTransaction(new Tool(1), tools, 1);
    fallBack.logTransaction(new Program(1), programs, 1);
  }

  // TODO maybe deredund some day?

  public void logTransaction(Tradeable product, double sellPrice, int soldQuantity) {
    current.logTransaction(product, sellPrice, soldQuantity);
  }

  public void logWorkerSellOffered(Product levelled, int quantity) {
    current.logWorkerSellOffered(levelled, quantity);
  }

  public void logOfferedQuantities(Product levelled, int quantity) {
    current.logOfferedQuantities(levelled, quantity);
  }

  public int getOfferedQuantities(Product levelled, int day) {
    if (days.isEmpty() || day == 0 || !days.contains(day - 1)) return 0;
    return days.get(day - 1).getOfferedQuantities(levelled);
  }

  public int getWorkerSellOffered(Product levelled, int day) {
    if (days.isEmpty() || day == 0 || !days.contains(day - 1)) return 0;
    return days.get(day - 1).getWorkerSellOffered(levelled);
  }

  void newDay() {
    if (current.day < Simulation.day()) {
      days.add(current);
      previous = current;
      current = new DayLog(Simulation.day(), this.fallBack);
    }
  }

  public DayLog getCurrent() {
    return current;
  }

  public Map<String, Double> mapLastAvgPrices() {
    return current.mapAvgPrices();
  }

  public Map<String, Double> mapLastMaxPrices() {
    return current.mapMaxPrices();
  }

  public Map<String, Double> mapLastMinPrices() {
    return current.mapMinPrices();
  }

  public double getAveragePrice(int day, Tradeable product) {
    if (days.isEmpty() || day <= 0 || !days.get(day - 1).soldThatDay(product)) return fallBack.getAveragePrice(product);
    return days.get(day - 1).getAveragePrice(product);
  }

  public int getSoldQuantity(int day, Tradeable product) {
    if (days.isEmpty() || day <= 0 || !days.get(day - 1).soldThatDay(product)) return fallBack.getSoldQuantity(product);
    return days.get(day - 1).getSoldQuantity(product);
  }

  public String printCurrent() {
    return current.toString();
  }
}
