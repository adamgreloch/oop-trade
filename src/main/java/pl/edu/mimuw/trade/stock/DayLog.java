package pl.edu.mimuw.trade.stock;

import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.Tradeable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.Double.MAX_VALUE;

public class DayLog {

  final int day;
  private final Set<Product> products;
  private final Map<Product, Double> max;
  private final Map<Product, Double> average;
  private final Map<Product, Double> min;
  private final Map<Product, Integer> quantities;

  public DayLog(int day) {
    this.day = day;
    this.products = new HashSet<>();
    this.max = new HashMap<>();
    this.average = new HashMap<>();
    this.min = new HashMap<>();
    this.quantities = new HashMap<>();
  }

  public void log(Tradeable levelled, double sellPrice, int soldQuantity) {
    Product product = levelled.generalize();

    this.products.add(product);
    this.max.put(product, Math.max(this.max.getOrDefault(product, 0.0), sellPrice));
    this.min.put(product, Math.min(this.min.getOrDefault(product, MAX_VALUE), sellPrice));
    this.average.put(product, (this.average.getOrDefault(product, 0.0)
            * this.quantities.getOrDefault(product, 0)) / soldQuantity + sellPrice);
    this.quantities.put(product, this.quantities.getOrDefault(product, 0) + soldQuantity);
  }

  public double min(Tradeable levelled, DayLog fallBack) {
    Product product = levelled.generalize();

    return this.min.getOrDefault(product, fallBack.min.get(product));
  }

  public boolean soldThatDay(Tradeable product) {
    return this.products.contains(product.generalize());
  }

  public double getAveragePrice(Tradeable product) {
    return this.average.get(product.generalize());
  }

  public int getSoldQuantity(Tradeable product) {
    return this.quantities.get(product.generalize());
  }

  @Override
  public String toString() {
    return "\nDayLog{" +
            "day=" + day +
            ", \nproducts=" + products +
            ", \nmax=" + max +
            ", \naverage=" + average +
            ", \nmin=" + min +
            ", \nquantities=" + quantities +
            '}';
  }
}
