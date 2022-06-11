package pl.edu.mimuw.trade.strategy.speculation;

import pl.edu.mimuw.trade.agents.Speculator;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.products.Tradeable;
import pl.edu.mimuw.trade.simulation.Offer;
import pl.edu.mimuw.trade.simulation.OfferFactory;
import pl.edu.mimuw.trade.simulation.Simulation;

import java.util.HashSet;
import java.util.Set;

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
      shape = functionShape(product, today);
      avg = Simulation.stock.getAveragePrice(today - 1, product);
      if (shape > 0) // Function is strictly convex, we buy.
        offers.add(OfferFactory.speculatorPurchaseOffer(speculator, product,
                PURCHASE_QUANTITY, avg * PURCHASE_FACTOR));
      if (shape < 0) {
        // Function is strictly concave, we sell.
        quantity = speculator.quantityOf(product);
        if (quantity > 0)
          offers.add(OfferFactory.speculatorSellOffer(speculator, product,
                  quantity, avg * SELL_FACTOR));
      }
    }
    return offers;
  }

  private double functionShape(Tradeable product, int today) {
    double first = Simulation.stock.getAveragePrice(today - 3, product);
    double mid = Simulation.stock.getAveragePrice(today - 2, product);
    double last = Simulation.stock.getAveragePrice(today - 1, product);
    return (first + last) / 2 - mid;
  }
}