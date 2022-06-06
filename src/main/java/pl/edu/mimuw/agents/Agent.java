package pl.edu.mimuw.agents;

import pl.edu.mimuw.Simulation;

public abstract class Agent {
  protected Simulation simulation;
  private int id;

  public Agent(Simulation simulation) {
    this.simulation = simulation;
    this.id = simulation.assignId();
  }

  /**
   * Dokonuje akcję w symulacji, czyli w zależności od sytuacji pracuje,
   * uczy się, handluje na giełdzie etc.
   */
  public abstract void act();

  public int id() {
    return id;
  }
}
