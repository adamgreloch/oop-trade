package pl.edu.mimuw.trade.strategy.speculation;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.agents.Speculator;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.products.Tradeable;
import pl.edu.mimuw.trade.simulation.Offer;
import pl.edu.mimuw.trade.simulation.OfferFactory;
import pl.edu.mimuw.trade.simulation.StockAnalysis;

import java.util.HashSet;
import java.util.Set;

public class AverageSpeculation extends SpeculationStrategy {
  private static final double FRESH_PURCHASE_FACTOR = 0.95;

  @SerializedName("historia_spekulanta_sredniego")
  private final int reachPast;

  public AverageSpeculation(int reachPast) {
    super("sredni");
    this.reachPast = reachPast;
  }

  public Set<Offer> makeOffers(Speculator speculator) {
    Set<Offer> offers = new HashSet<>();
    for (Tradeable product : ProductFactory.previewTradeable())
      offers.addAll(this.constructOffers(speculator, product));
    return offers;
  }

  private Set<Offer> constructOffers(Speculator speculator, Tradeable product) {
    Set<Offer> offers = new HashSet<>();
    double avg = StockAnalysis.avgPrice(product, this.reachPast);
    int quantity = speculator.ownsQuantity(product);

    if (quantity > 0) {
      offers.add(OfferFactory.speculatorPurchaseOffer(speculator, product,
              PURCHASE_QUANTITY, avg * PURCHASE_FACTOR));
      offers.add(OfferFactory.speculatorSellOffer(speculator, product,
              quantity, avg * SELL_FACTOR));
    }
    else
      offers.add(OfferFactory.speculatorPurchaseOffer(speculator, product,
              PURCHASE_QUANTITY, avg * FRESH_PURCHASE_FACTOR));

    return offers;
  }
}
