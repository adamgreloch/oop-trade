package pl.edu.mimuw.strategy;

import pl.edu.mimuw.Offer;
import pl.edu.mimuw.agents.Worker;

import java.util.Set;

/**
 * Interfejs strategii konsumpcji, czyli wskazywanie, ile czego Robotnik ma kupować i w jaki sposób ma
 * stosować programy komputerowe.
 */
public interface PurchaseStrategy {

  Set<Offer> purchasesToOffer(Worker worker);

}
