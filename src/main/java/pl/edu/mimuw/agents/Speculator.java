package pl.edu.mimuw.agents;

import pl.edu.mimuw.agents.speculation.SpeculationStrategy;
import pl.edu.mimuw.stock.Simulation;

public class Speculator extends Agent {

  SpeculationStrategy speculationStrategy;

  public Speculator(Simulation simulation, SpeculationStrategy speculationStrategy) {
    super(simulation);
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
