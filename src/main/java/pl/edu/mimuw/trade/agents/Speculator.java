package pl.edu.mimuw.trade.agents;

import pl.edu.mimuw.trade.simulation.Stock;
import pl.edu.mimuw.trade.strategy.speculation.SpeculationStrategy;

public class Speculator extends Agent {

  SpeculationStrategy speculationStrategy;

  public Speculator(int id, Stock stock, SpeculationStrategy speculationStrategy) {
    super(id, stock);
    this.speculationStrategy = speculationStrategy;
  }

  public void act() {
  }

  public void makeOffers() {
    stock.addOffer(speculationStrategy.makeOffers(this), this);
  }

  public void finishDay() {
  }

  public void spendDiamonds(double amount) {
  }

  @Override
  public String toString() {
    return "Speculator (Agent " + id() + ")";
  }
}
