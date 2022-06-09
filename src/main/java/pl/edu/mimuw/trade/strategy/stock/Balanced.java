package pl.edu.mimuw.trade.strategy.stock;

import pl.edu.mimuw.trade.stock.OfferQueue;

import java.util.List;
import java.util.Set;

public class Balanced extends StockStrategy {

  public Balanced() {
    super("zrownowazona");
  }

  public List<OfferQueue> sortWorkerOffers(Set<OfferQueue> workerOffers) {
    throw new IllegalArgumentException("TODO");
  }
}
