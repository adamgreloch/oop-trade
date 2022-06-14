package pl.edu.mimuw.trade.strategy.production;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.*;
import pl.edu.mimuw.trade.simulation.StockAnalysis;
import pl.edu.mimuw.trade.strategy.ProductionStrategy;

public class AverageProduction extends ProductionStrategy {
  @SerializedName("historia_sredniej_produkcji")
  private int reachPast;

  public AverageProduction() {
    super("sredniak");
  }

  public Product pickToProduce(Worker worker) {
    double maxAvg = 0, avg;
    Product picked = null;
    for (Tradeable product : ProductFactory.previewTradeable()) {
      avg = StockAnalysis.avgPriceOfDays(product, this.reachPast);
      if (avg > maxAvg) {
        maxAvg = avg;
        picked = product;
      }
    }
    return picked;
  }
}
