package pl.edu.mimuw.stock;

import java.util.HashSet;
import java.util.Set;

public class SalePurchaseOffers {
  private final Set<Offer> workerSales;
  private final Set<Offer> workerPurchases;
  private final Set<Offer> speculatorSales;
  private final Set<Offer> speculatorPurchases;

  public SalePurchaseOffers() {
    this.workerSales = new HashSet<>();
    this.workerPurchases = new HashSet<>();
    this.speculatorSales = new HashSet<>();
    this.speculatorPurchases = new HashSet<>();
  }

  public void add(Offer offer) {
    if (offer.purchaseOffer) {
      if (offer.workerOffer)
        workerPurchases.add(offer);
      else
        speculatorPurchases.add(offer);
    }
    else {
      if (offer.workerOffer)
        workerSales.add(offer);
      else
        speculatorSales.add(offer);
    }
  }

  public Set<Offer> workerSale() {
    return workerSales;
  }

  public Set<Offer> workerPurchase() {
    return workerPurchases;
  }

  public Set<Offer> speculatorSales() {
    return speculatorSales;
  }

  public Set<Offer> speculatorPurchases() {
    return speculatorPurchases;
  }
}
