package pl.edu.mimuw.agents;

import pl.edu.mimuw.Simulation;
import pl.edu.mimuw.agents.trade.TradeStrategy;
import pl.edu.mimuw.products.Bag;

public class Speculator extends Agent {

  TradeStrategy tradeStrategy;
  private final Bag bag;

  public Speculator(Simulation simulation, TradeStrategy tradeStrategy) {
    super(simulation);
    this.bag = new Bag();
    this.tradeStrategy = tradeStrategy;
  }

  public void act() {
  }

  public int diamonds() {
    return bag.countDiamonds();
  }
}
