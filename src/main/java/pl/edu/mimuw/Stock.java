package pl.edu.mimuw;

import pl.edu.mimuw.strategy.StockStrategy;

import java.util.Set;

public class Stock {

  private final StockStrategy stockStrategy;

  public Stock(StockStrategy stockStrategy) {
    this.stockStrategy = stockStrategy;
  }

  public void hearPurchaseOffers(Set<Offer> offers) {

  }

  public void hearSaleOffers(Set<Offer> offers) {

  }
}
