package pl.edu.mimuw.agents;

import pl.edu.mimuw.Simulation;
import pl.edu.mimuw.agents.trade.TradeStrategy;

public class Speculator extends Agent {

  TradeStrategy tradeStrategy;

  public Speculator(Simulation simulation, TradeStrategy tradeStrategy) {
    super(simulation);
    this.tradeStrategy = tradeStrategy;
  }

  public void act() {
  }

  @Override
  public String toString() {
    return "Speculator " + id();
  }
}
