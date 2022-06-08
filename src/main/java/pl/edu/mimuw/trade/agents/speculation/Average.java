package pl.edu.mimuw.trade.agents.speculation;

import pl.edu.mimuw.trade.agents.Speculator;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.products.Tradeable;
import pl.edu.mimuw.trade.stock.Offer;
import pl.edu.mimuw.trade.stock.Simulation;
import pl.edu.mimuw.trade.stock.Stock;

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
    for (Tradeable product : ProductFactory.previewTradeable())
      offers.addAll(constructOffers(speculator, product));
    return offers;
  }

  private Set<Offer> constructOffers(Speculator speculator, Tradeable product) {
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

  private double calculateAverage(Tradeable product) {
    double sum = 0;
    int sold = 0;
    int reached;
    for (int day = 1; day <= reachPast; day++) {
      reached = Math.max(Simulation.day() - day, 0);
      sum += stock.getAveragePrice(reached, product);
      sold += stock.getSoldQuantity(reached, product);
    }
    return sum / sold;
  }
}
