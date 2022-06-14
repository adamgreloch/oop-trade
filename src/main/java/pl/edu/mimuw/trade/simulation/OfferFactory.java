package pl.edu.mimuw.trade.simulation;

import pl.edu.mimuw.trade.agents.*;
import pl.edu.mimuw.trade.products.Tradeable;

import static pl.edu.mimuw.trade.simulation.OfferType.SELL;

public class OfferFactory {
  public static Offer workerPurchaseOffer(Worker issuer, Tradeable product, int quantity) {
    return new Offer(issuer, product, quantity, 0, true, true);
  }

  public static Offer workerSellOffer(Worker issuer, Tradeable product, int quantity) {
    return new Offer(issuer, product, quantity, 0, true, false);
  }

  public static Offer speculatorPurchaseOffer(Speculator issuer, Tradeable product, int quantity, double price) {
    return new Offer(issuer, product, quantity, price, false, true);
  }

  public static Offer speculatorSellOffer(Speculator issuer, Tradeable product, int quantity, double price) {
    return new Offer(issuer, product, quantity, price, false, false);
  }

  public static Offer completingBuyOffer(Agent issuer, Offer offer, double price) {
    assert offer.offerType == SELL;
    return new Offer(issuer, offer, price);
  }
}
