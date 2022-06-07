package pl.edu.mimuw.agents.speculation;

import pl.edu.mimuw.agents.Speculator;
import pl.edu.mimuw.products.ProductFactory;
import pl.edu.mimuw.products.TradeableProduct;
import pl.edu.mimuw.stock.Offer;
import pl.edu.mimuw.stock.Simulation;
import pl.edu.mimuw.stock.Stock;

import java.util.HashSet;
import java.util.Set;

public class Average implements SpeculationStrategy {
  private static final int PURCHASE_QUANTITY = 100;
  private static final double FRESH_PURCHASE_FACTOR = 0.95;
  private static final double PURCHASE_FACTOR = 0.9;
  private static final double SELL_FACTOR = 1.1;
  private final int reachPast;
  private final Stock stock;

  public Average(Simulation simulation, int reachPast) {
    this.reachPast = reachPast;
    this.stock = simulation.stock();
  }

  public Set<Offer> makeOffers(Speculator speculator) {
    Set<Offer> offers = new HashSet<>();
    for (TradeableProduct product : ProductFactory.previewTradeable())
      offers.addAll(constructOffers(speculator, product));
    return offers;
  }

  private Set<Offer> constructOffers(Speculator speculator, TradeableProduct product) {
    Set<Offer> offers = new HashSet<>();
    double avg = calculateAverage(product);
    int quantity = speculator.hasQuantity(product);

    if (quantity > 0) {
      offers.add(new Offer(speculator, product, PURCHASE_QUANTITY, avg * PURCHASE_FACTOR, true));
      offers.add(new Offer(speculator, product, quantity, avg * SELL_FACTOR, false));
    }
    else
      offers.add(new Offer(speculator, product, PURCHASE_QUANTITY, avg * FRESH_PURCHASE_FACTOR, true));

    return offers;
  }

  private double calculateAverage(TradeableProduct product) {
    double sum = 0;
    int sold = 0;
    int reached = 0;
    for (int day = 1; day <= reachPast; day++) {
      reached = Math.max(Simulation.day() - day, 0);
      sum += stock.getAveragePrice(reached, product);
      sold += stock.getSoldQuantity(reached, product);
    }
    return sum / sold;
  }
}
