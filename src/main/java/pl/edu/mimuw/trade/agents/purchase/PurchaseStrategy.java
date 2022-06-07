package pl.edu.mimuw.trade.agents.purchase;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.stock.Offer;

import java.util.Set;

/**
 * Interfejs strategii konsumpcji, czyli wskazywanie, ile czego Robotnik ma kupować i w jaki sposób ma
 * stosować programy komputerowe.
 */
public interface PurchaseStrategy {

  Set<Offer> purchasesToOffer(Worker worker);

}
