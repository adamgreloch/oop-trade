package pl.edu.mimuw.agents.purchase;

import pl.edu.mimuw.agents.Worker;
import pl.edu.mimuw.stock.Offer;

import java.util.Set;

/**
 * Interfejs strategii konsumpcji, czyli wskazywanie, ile czego Robotnik ma kupować i w jaki sposób ma
 * stosować programy komputerowe.
 */
public interface PurchaseStrategy {

  Set<Offer> purchasesToOffer(Worker worker);

}
