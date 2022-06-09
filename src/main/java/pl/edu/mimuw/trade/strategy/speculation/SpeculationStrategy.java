package pl.edu.mimuw.trade.strategy.speculation;

import pl.edu.mimuw.trade.agents.Speculator;
import pl.edu.mimuw.trade.stock.Offer;
import pl.edu.mimuw.trade.strategy.Strategy;

import java.util.Set;

/**
 * Interfejs strategii handlowania Spekulanta.
 */
public abstract class SpeculationStrategy extends Strategy {

  public SpeculationStrategy(String name) {
    super(name);
  }

  public abstract Set<Offer> makeOffers(Speculator speculator);
}
