package pl.edu.mimuw.trade.simulation;

import pl.edu.mimuw.trade.products.*;

import java.util.HashMap;
import java.util.Map;

public class StockLog {

  private final Map<Integer, DayLog> days;
  private transient final DayLog fallBack;
  private transient DayLog previous;
  private transient DayLog current;

  public StockLog(DayLog fallBack) {
    this.days = new HashMap<>();
    this.fallBack = fallBack;
    this.current = new DayLog(1);
    this.days.put(0, fallBack);
    this.days.put(1, this.current);
    this.previous = fallBack;
  }

  /**
   * @return Price of the lowest product purchase price the day before or price from zero-day.
   */
  public double previousLowest(Tradeable product) {
    return this.previous.min(product, this.fallBack);
  }

  public void setFallBackPrices(double food, double clothes, double tools, double programs) {
    this.fallBack.logTransaction(new Food(1), food, 1);
    this.fallBack.logTransaction(new Clothes(1), clothes, 1);
    this.fallBack.logTransaction(new Tool(1), tools, 1);
    this.fallBack.logTransaction(new Program(1), programs, 1);
  }

  // TODO maybe deredund some day?

  public void logTransaction(Tradeable product, double sellPrice, int soldQuantity) {
    this.current.logTransaction(product, sellPrice, soldQuantity);
  }

  public void logWorkerSellOffered(Tradeable levelled, int quantity) {
    this.current.logWorkerSellOffered(levelled, quantity);
  }

  public void logOfferedQuantities(Tradeable levelled, int quantity) {
    this.current.logOfferedQuantities(levelled, quantity);
  }

  public int getOfferedQuantities(Tradeable levelled, int day) {
    if (this.days.isEmpty()) return 0;
    return this.days.get(day).getOfferedQuantities(levelled);
  }

  public int getWorkerSellOffered(Tradeable levelled, int day) {
    if (this.days.isEmpty() || day <= 0) return 0;
    return this.days.get(day).getWorkerSellOffered(levelled);
  }

  void newDay() {
    if (this.current.day < Simulation.day()) {
      this.previous = this.current;
      this.current = new DayLog(Simulation.day());
      this.days.put(Simulation.day(), this.current);
    }
  }

  public DayLog getCurrent() {
    return this.current;
  }

  public Map<String, Double> mapLastAvgPrices() {
    return this.current.mapAvgPrices();
  }

  public Map<String, Double> mapLastMaxPrices() {
    return this.current.mapMaxPrices();
  }

  public Map<String, Double> mapLastMinPrices() {
    return this.current.mapMinPrices();
  }

  private boolean shouldFallback(int day, Tradeable product) {
    return day < 0 || !this.days.get(day).soldThatDay(product);
  }

  public double getAveragePrice(int day, Tradeable product) {
    if (this.shouldFallback(day, product)) return this.fallBack.getAveragePrice(product);
    return this.days.get(day).getAveragePrice(product);
  }

  public int getSoldQuantity(int day, Tradeable product) {
    if (this.shouldFallback(day, product)) return this.fallBack.getSoldQuantity(product);
    return this.days.get(day).getSoldQuantity(product);
  }

  public String printCurrent() {
    return this.current.toString();
  }
}
