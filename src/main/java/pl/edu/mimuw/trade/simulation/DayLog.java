package pl.edu.mimuw.trade.simulation;

import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.Tradeable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.Double.MAX_VALUE;

public class DayLog {

  int day;
  private final Set<Product> products;
  private final Map<Product, Double> max;
  private final Map<Product, Double> average;
  private final Map<Product, Double> min;
  private final Map<Product, Integer> quantitiesSold;
  private final Map<Product, Integer> quantitiesOfferedByWorkers;
  private final Map<Product, Integer> quantitiesInOffers;
  private DayLog fallBack;

  public DayLog() {
    this.products = new HashSet<>();
    this.max = new HashMap<>();
    this.average = new HashMap<>();
    this.min = new HashMap<>();
    this.quantitiesSold = new HashMap<>();
    this.quantitiesOfferedByWorkers = new HashMap<>();
    this.quantitiesInOffers = new HashMap<>();
  }

  public DayLog(int day, DayLog fallBack) {
    this();
    this.day = day;
    this.fallBack = fallBack;
  }

  public void logTransaction(Product levelled, double sellPrice, int soldQuantity) {
    assert levelled instanceof Tradeable;
    Product product = levelled.generalize();

    this.products.add(product);
    this.max.put(product, Math.max(this.max.getOrDefault(product, 0.0), sellPrice));
    this.min.put(product, Math.min(this.min.getOrDefault(product, MAX_VALUE), sellPrice));
    double prevAvg = this.average.getOrDefault(product, 0.0);
    int prevTotalQuantity = this.quantitiesSold.getOrDefault(product, 0);
    this.average.put(product, (prevAvg * prevTotalQuantity + soldQuantity * sellPrice)
            / (prevTotalQuantity + soldQuantity));
    this.quantitiesSold.put(product, this.quantitiesSold.getOrDefault(product, 0) + soldQuantity);
  }

  public void logWorkerSellOffered(Product levelled, int quantity) {
    assert levelled instanceof Tradeable;
    Product product = levelled.generalize();
    this.quantitiesOfferedByWorkers.put(product, this.quantitiesOfferedByWorkers.getOrDefault(product, 0) + quantity);
  }

  public void logOfferedQuantities(Product levelled, int quantity) {
    assert levelled instanceof Tradeable;
    Product product = levelled.generalize();
    this.quantitiesInOffers.put(product, this.quantitiesInOffers.getOrDefault(product, 0) + quantity);
  }

  public int getOfferedQuantities(Product product) {
    if (!this.quantitiesInOffers.containsKey(product.generalize())) return 0;
    return this.quantitiesInOffers.get(product.generalize());
  }

  public int getWorkerSellOffered(Product product) {
    if (!this.quantitiesOfferedByWorkers.containsKey(product.generalize())) return 0;
    return this.quantitiesOfferedByWorkers.get(product.generalize());
  }

  public double min(Tradeable levelled, DayLog fallBack) {
    Product product = levelled.generalize();
    return this.min.getOrDefault(product, fallBack.min.get(product));
  }

  public Map<String, Double> mapAvgPrices() {
    return mapPrices(this.average);
  }

  public Map<String, Double> mapMaxPrices() {
    return mapPrices(this.max);
  }

  public Map<String, Double> mapMinPrices() {
    return mapPrices(this.min);
  }

  private Map<String, Double> mapPrices(Map<Product, Double> prices) {
    Map<String, Double> res = new HashMap<>();
    for (Product product : prices.keySet())
      res.put(product.productName(), prices.get(product));
    return res;
  }

  public boolean soldThatDay(Tradeable product) {
    return this.products.contains(product.generalize());
  }

  public double getAveragePrice(Tradeable product) {
    return this.average.get(product.generalize());
  }

  public int getSoldQuantity(Tradeable product) {
    return this.quantitiesSold.get(product.generalize());
  }

  @Override
  public String toString() {
    return "\nDayLog{" +
            "day=" + day +
            ", \nproducts=" + products +
            ", \nmax=" + max +
            ", \naverage=" + average +
            ", \nmin=" + min +
            ", \nquantities=" + quantitiesSold +
            '}';
  }
}
