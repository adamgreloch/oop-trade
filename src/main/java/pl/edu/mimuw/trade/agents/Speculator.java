package pl.edu.mimuw.trade.agents;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.simulation.Simulation;
import pl.edu.mimuw.trade.strategy.SpeculationStrategy;

public class Speculator extends Agent {

  @SerializedName("kariera")
  SpeculationStrategy speculationStrategy;

  public Speculator() {
    this.actionPriority = 1;
  }

  public void act() {
  }

  public void makeOffers() {
    this.saleBag = this.storageBag;
    Simulation.stock.addOffer(this.speculationStrategy.makeOffers(this), this);
  }

  public void finishDay() {
  }

  public void spendDiamonds(double amount) {
  }

  @Override
  public String toString() {
    return "Speculator (Agent " + this.id() + ")";
  }
}
