package pl.edu.mimuw.trade.agents;

import pl.edu.mimuw.trade.agents.speculation.SpeculationStrategy;
import pl.edu.mimuw.trade.stock.Simulation;

public class Speculator extends Agent {

  SpeculationStrategy speculationStrategy;

  public Speculator(int id, Simulation simulation, SpeculationStrategy speculationStrategy) {
    super(id, simulation);
    this.speculationStrategy = speculationStrategy;
  }

  public void act() {
  }

  public void makeOffers() {
    simulation.stock().addOffer(speculationStrategy.makeOffers(this), this);
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
