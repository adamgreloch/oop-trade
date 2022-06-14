package pl.edu.mimuw.trade.simulation;

import pl.edu.mimuw.trade.products.Tradeable;

import static pl.edu.mimuw.trade.simulation.Simulation.stock;

public class StockAnalysis {

  public static double avgPriceOfDays(Tradeable product, int ofLastDays) {
    if (ofLastDays < 1) throw new IllegalArgumentException();

    double sum = 0;
    int soldTotal = 0, sold;
    int reached;
    for (int day = 1; day <= ofLastDays; day++) {
      reached = Math.max(Simulation.day() - day, 0);
      sold = stock.log.getSoldQuantity(reached, product);
      soldTotal += sold;
      sum += sold * stock.log.getAveragePrice(reached, product);
      if (reached == 0) break;
    }
    return sum / soldTotal;
  }

  public static int mentionsInOffers(Tradeable product, int inLastDays) {
    int totalMentions = 0;
    int today = Simulation.day();
    for (int i = 1; i <= inLastDays; i++)
      totalMentions += stock.log.getOfferedQuantities(product, today - i);
    return totalMentions;
  }
}
