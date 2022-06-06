package pl.edu.mimuw.agents;

import pl.edu.mimuw.Simulation;
import pl.edu.mimuw.products.Bag;

public abstract class Agent {
  protected Simulation simulation;
  private final int id;
  protected Bag saleBag;
  protected Bag storageBag;

  public Agent(Simulation simulation) {
    this.simulation = simulation;
    this.id = simulation.assignId();
    this.saleBag = new Bag();
    this.storageBag = saleBag;
  }

  /**
   * Dokonuje akcję w symulacji, czyli w zależności od sytuacji pracuje,
   * uczy się, handluje na giełdzie etc.
   */
  public abstract void act();

  public int id() {
    return id;
  }

  public double diamonds() {
    return storageBag.countDiamonds();
  }

  public void earnDiamonds(double amount) {
    storageBag.storeDiamonds(amount);
  }

  public void spendDiamonds(double amount) {
    storageBag.takeDiamonds(amount);
  }
}
