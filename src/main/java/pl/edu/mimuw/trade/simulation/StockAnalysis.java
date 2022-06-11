package pl.edu.mimuw.trade.simulation;

import pl.edu.mimuw.trade.products.Tradeable;

public class StockAnalysis {

  public static double avgPrice(Tradeable product, int ofLastDays) {
    double sum = 0;
    int soldTotal = 0, sold;
    int reached;
    for (int day = 1; day <= ofLastDays; day++) {
      reached = Math.max(Simulation.day() - day, 0);
      sold = Simulation.stock.getSoldQuantity(reached, product);
      soldTotal += sold;
      sum += sold * Simulation.stock.getAveragePrice(reached, product);
    }
    return sum / soldTotal;
  }

  public static int mentionsInOffers(Tradeable product, int inLastDays) {
    int totalMentions = 0;
    int today = Simulation.day();
    for (int i = 1; i <= inLastDays; i++)
      totalMentions += Simulation.stock.getOfferedQuantities(product, today - i);
    return totalMentions;
  }
}
