package pl.edu.mimuw.trade.strategy.speculation;

import pl.edu.mimuw.trade.agents.Speculator;
import pl.edu.mimuw.trade.stock.Offer;

import java.util.Set;

/**
 * Interfejs strategii handlowania Spekulanta.
 */
public interface SpeculationStrategy {
  Set<Offer> makeOffers(Speculator speculator);
}
