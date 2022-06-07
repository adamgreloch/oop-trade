package pl.edu.mimuw.agents.speculation;

import pl.edu.mimuw.agents.Speculator;
import pl.edu.mimuw.stock.Offer;

import java.util.Set;

/**
 * Interfejs strategii handlowania Spekulanta.
 */
public interface SpeculationStrategy {
  Set<Offer> makeOffers(Speculator speculator);
}
