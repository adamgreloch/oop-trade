package pl.edu.mimuw.trade.strategy.stock;

import pl.edu.mimuw.trade.stock.OfferQueue;
import pl.edu.mimuw.trade.strategy.Strategy;

import java.util.List;
import java.util.Set;

public abstract class StockStrategy extends Strategy {

  public StockStrategy(String name) {
    super(name);
  }

  public abstract List<OfferQueue> sortWorkerOffers(Set<OfferQueue> workerOffers);
}
