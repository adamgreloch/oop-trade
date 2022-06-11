package pl.edu.mimuw.trade.strategy.speculation;

import pl.edu.mimuw.trade.agents.Speculator;
import pl.edu.mimuw.trade.simulation.Offer;
import pl.edu.mimuw.trade.strategy.Strategy;

import java.util.Set;

/**
 * Interfejs strategii handlowania Spekulanta.
 */
public abstract class SpeculationStrategy extends Strategy {
  protected static final int PURCHASE_QUANTITY = 100;
  protected static final double PURCHASE_FACTOR = 0.9;
  protected static final double SELL_FACTOR = 1.1;

  public SpeculationStrategy(String name) {
    super(name);
  }

  public abstract Set<Offer> makeOffers(Speculator speculator);
}
