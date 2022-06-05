package pl.edu.mimuw.agents;

import pl.edu.mimuw.Simulation;
import pl.edu.mimuw.products.Bag;
import pl.edu.mimuw.strategy.TradeStrategy;

public class Speculator extends Agent {

  TradeStrategy tradeStrategy;
  private Bag bag;

  public Speculator(Simulation simulation, TradeStrategy tradeStrategy) {
    super(simulation);
    this.bag = new Bag();
    this.tradeStrategy = tradeStrategy;
  }

  public void act() {
  }
}
