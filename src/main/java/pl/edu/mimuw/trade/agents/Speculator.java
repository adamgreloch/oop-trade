package pl.edu.mimuw.trade.agents;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.simulation.Simulation;
import pl.edu.mimuw.trade.simulation.Stock;
import pl.edu.mimuw.trade.strategy.speculation.SpeculationStrategy;

public class Speculator extends Agent {

  @SerializedName("kariera")
  SpeculationStrategy speculationStrategy;

  public Speculator() {
    super();
  }

  // TODO find out why Speculator has a null storageBag

  public Speculator(int id, Stock stock, SpeculationStrategy speculationStrategy) {
    super(id, stock);
    this.speculationStrategy = speculationStrategy;
  }

  public void act() {
  }

  public void makeOffers() {
    Simulation.stock.addOffer(speculationStrategy.makeOffers(this), this);
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
