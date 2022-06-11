package pl.edu.mimuw.trade.strategy.stock;

import pl.edu.mimuw.trade.simulation.OfferQueue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Socialist extends StockStrategy {

  public Socialist() {
    super("socjalistyczna");
  }

  private static int compare(OfferQueue a, OfferQueue b) {
    double cmp = a.issuer().diamonds() - b.issuer().diamonds();
    if (cmp == 0) return b.issuer().id() - a.issuer().id();
    return (int) cmp;
  }

  public List<OfferQueue> sortWorkerOffers(Set<OfferQueue> workerOffers) {
    return workerOffers.stream().sorted(Socialist::compare).collect(Collectors.toList());
  }
}
