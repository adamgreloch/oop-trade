package pl.edu.mimuw.trade.strategy.stock;

import pl.edu.mimuw.trade.simulation.OfferQueue;
import pl.edu.mimuw.trade.simulation.Simulation;

import java.util.List;
import java.util.Set;

public class Balanced extends StockStrategy {
  private final Socialist socialist;
  private final Capitalist capitalist;

  public Balanced() {
    super("zrownowazona");
    this.socialist = new Socialist();
    this.capitalist = new Capitalist();
  }

  public List<OfferQueue> sortWorkerOffers(Set<OfferQueue> workerOffers) {
    StockStrategy picked = capitalist;
    if (Simulation.day() % 2 == 0)
      picked = socialist;
    return picked.sortWorkerOffers(workerOffers);
  }
}
