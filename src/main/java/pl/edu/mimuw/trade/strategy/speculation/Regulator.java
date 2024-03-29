package pl.edu.mimuw.trade.strategy.speculation;

import pl.edu.mimuw.trade.agents.Speculator;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.products.Tradeable;
import pl.edu.mimuw.trade.simulation.*;
import pl.edu.mimuw.trade.strategy.SpeculationStrategy;

import java.util.HashSet;
import java.util.Set;

import static pl.edu.mimuw.trade.simulation.Simulation.stock;

/**
 * Is it just me, or the "regulating" formula actually deregulates the stock even more?
 * Regulator?
 * More like
 * Deregulator
 * Hahaha
 * *leaves*
 */
public class Regulator extends SpeculationStrategy {

  public Regulator() {
    super("regulujacy");
  }

  public Set<Offer> makeOffers(Speculator speculator) {
    Set<Offer> offers = new HashSet<>();
    int today = Simulation.day();
    if (today == 1) return offers;

    double factor, avg;
    int quantity;

    for (Tradeable product : ProductFactory.previewTradeable()) {
      avg = stock.log.getAveragePrice(today - 1, product);
      factor = this.calculateFactor(product, today);
      offers.add(OfferFactory.speculatorPurchaseOffer(speculator, product,
              PURCHASE_QUANTITY, avg * factor * PURCHASE_FACTOR));
      quantity = speculator.ownsQuantity(product);
      if (quantity > 0)
        offers.add(OfferFactory.speculatorSellOffer(speculator, product,
                quantity, avg * factor * SELL_FACTOR));
    }
    return offers;
  }

  private double calculateFactor(Tradeable product, int today) {
    double todays = stock.log.getWorkerSellOffered(product, today);
    double yesterdays = stock.log.getWorkerSellOffered(product, today - 1);
    return todays / Math.max(yesterdays, 1);
  }
}
