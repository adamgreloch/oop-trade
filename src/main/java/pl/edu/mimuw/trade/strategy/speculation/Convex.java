package pl.edu.mimuw.trade.strategy.speculation;

import pl.edu.mimuw.trade.agents.Speculator;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.products.Tradeable;
import pl.edu.mimuw.trade.simulation.*;
import pl.edu.mimuw.trade.strategy.SpeculationStrategy;

import java.util.HashSet;
import java.util.Set;

import static pl.edu.mimuw.trade.simulation.Simulation.stock;

public class Convex extends SpeculationStrategy {

  public Convex() {
    super("wypukly");
  }

  public Set<Offer> makeOffers(Speculator speculator) {
    Set<Offer> offers = new HashSet<>();

    int today = Simulation.day();
    double shape, avg;
    int quantity;
    for (Tradeable product : ProductFactory.previewTradeable()) {
      shape = this.functionShape(product, today);
      avg = stock.log.getAveragePrice(today - 1, product);
      if (shape > 0) // Function is strictly convex, we buy.
        offers.add(OfferFactory.speculatorPurchaseOffer(speculator, product,
                PURCHASE_QUANTITY, avg * PURCHASE_FACTOR));
      if (shape < 0) {
        // Function is strictly concave, we sell.
        quantity = speculator.ownsQuantity(product);
        if (quantity > 0)
          offers.add(OfferFactory.speculatorSellOffer(speculator, product,
                  quantity, avg * SELL_FACTOR));
      }
    }
    return offers;
  }

  private double functionShape(Tradeable product, int today) {
    double first = stock.log.getAveragePrice(today - 3, product);
    double mid = stock.log.getAveragePrice(today - 2, product);
    double last = stock.log.getAveragePrice(today - 1, product);
    return (first + last) / 2 - mid;
  }
}
