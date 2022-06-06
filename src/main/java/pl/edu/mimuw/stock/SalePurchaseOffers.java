package pl.edu.mimuw.stock;

import pl.edu.mimuw.stock.Offer;

import java.util.HashSet;
import java.util.Set;

public class SalePurchaseOffers {
  private Set<Offer> sale;
  private Set<Offer> purchase;

  public SalePurchaseOffers() {
    this.sale = new HashSet<>();
    this.purchase = new HashSet<>();
  }

  public void add(Offer offer) {
    if (offer.isPurchaseOffer)
      purchase.add(offer);
    else
      sale.add(offer);
  }
}
