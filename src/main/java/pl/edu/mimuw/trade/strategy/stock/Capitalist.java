package pl.edu.mimuw.trade.strategy.stock;

import pl.edu.mimuw.trade.simulation.OfferQueue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Capitalist extends StockStrategy {

  public Capitalist() {
    super("kapitalistyczna");
  }

  private static int compare(OfferQueue a, OfferQueue b) {
    double cmp = b.issuer().diamonds() - a.issuer().diamonds();
    if (cmp == 0) return a.issuer().id() - b.issuer().id();
    return (int) cmp;
  }

  public List<OfferQueue> sortWorkerOffers(Set<OfferQueue> workerOffers) {
    return workerOffers.stream().sorted(Capitalist::compare).collect(Collectors.toList());
  }
}
