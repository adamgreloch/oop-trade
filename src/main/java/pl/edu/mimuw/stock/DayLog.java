package pl.edu.mimuw.stock;

import pl.edu.mimuw.products.Product;
import pl.edu.mimuw.products.TradeableProduct;

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

  public void log(TradeableProduct product, double sellPrice, int soldQuantity) {
    this.products.add(product);
    this.max.put(product, Math.max(this.max.getOrDefault(product, 0.0), sellPrice));
    this.min.put(product, Math.min(this.min.getOrDefault(product, MAX_VALUE), sellPrice));
    this.average.put(product, (this.average.getOrDefault(product, 0.0)
            * this.quantities.getOrDefault(product, 0)) / soldQuantity + sellPrice);
    this.quantities.put(product, this.quantities.getOrDefault(product, 0) + soldQuantity);
  }

  public void fallBack(DayLog fallBack) {
    for (Product product : fallBack.products)
      if (!this.products.contains(product)) {
        this.max.put(product, fallBack.max.get(product));
        this.average.put(product, fallBack.average.get(product));
        this.min.put(product, fallBack.min.get(product));
      }
  }
}
