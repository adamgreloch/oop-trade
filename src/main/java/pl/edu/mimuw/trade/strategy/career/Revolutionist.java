package pl.edu.mimuw.trade.strategy.career;

import pl.edu.mimuw.trade.agents.Occupation;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.agents.occupations.OccupationFactory;
import pl.edu.mimuw.trade.products.*;
import pl.edu.mimuw.trade.simulation.Simulation;
import pl.edu.mimuw.trade.simulation.StockAnalysis;
import pl.edu.mimuw.trade.strategy.CareerStrategy;

public class Revolutionist extends CareerStrategy {
  private static final int MODULO = 17;
  private static final int CHANGE_PER_DAYS = 7;

  public Revolutionist() {
    super("rewolucjonista");
  }

  public boolean isCareerChangePending(Worker worker) {
    return Simulation.day() % CHANGE_PER_DAYS == 0;
  }

  public Occupation pickCareer(Worker worker) {
    int n = Math.max(1, worker.id() % MODULO);
    Product picked = null;
    int max = 0, quantity;

    for (Tradeable product : ProductFactory.previewTradeable()) {
      quantity = StockAnalysis.mentionsInOffers(product, n);
      if (quantity > max) {
        max = quantity;
        picked = product;
      }
    }

    return OccupationFactory.findOccupationByProduct(picked);
  }
}
