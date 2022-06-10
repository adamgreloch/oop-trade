package pl.edu.mimuw.trade.strategy.stock;

import pl.edu.mimuw.trade.simulation.OfferQueue;

import java.util.List;
import java.util.Set;

public class Socialist extends StockStrategy {

  public Socialist() {
    super("socjalistyczna");
  }

  public List<OfferQueue> sortWorkerOffers(Set<OfferQueue> workerOffers) {
    throw new IllegalArgumentException("TODO");
  }
}
