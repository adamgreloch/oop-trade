package pl.edu.mimuw.stock;

import pl.edu.mimuw.products.TradeableProduct;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Stock {

  private final StockStrategy stockStrategy;

  private final Map<TradeableProduct, SalePurchaseOffers> offersPending;

  public Stock(StockStrategy stockStrategy) {
    this.stockStrategy = stockStrategy;
    this.offersPending = new HashMap<>();
  }

  public void addOffer(Set<Offer> offers) {
    for (Offer offer : offers) {
      if (!offersPending.containsKey(offer.product()))
        offersPending.put(offer.product(), new SalePurchaseOffers());
      offersPending.get(offer.product()).add(offer);
    }
  }

  public void processTransactions() {
    stockStrategy.processTransactions(offersPending);
  }

}
