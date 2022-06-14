package pl.edu.mimuw.trade.strategy;

import pl.edu.mimuw.trade.simulation.OfferQueue;

import java.util.List;
import java.util.Set;

public abstract class StockStrategy extends Strategy {

  public StockStrategy(String name) {
    super(name);
  }

  public abstract List<OfferQueue> sortWorkerOffers(Set<OfferQueue> workerOffers);
}
