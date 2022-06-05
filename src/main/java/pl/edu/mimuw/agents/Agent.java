package pl.edu.mimuw.agents;

import pl.edu.mimuw.Simulation;

public abstract class Agent {
  protected Simulation simulation;

  public Agent(Simulation simulation) {
    this.simulation = simulation;
  }

  /**
   * Dokonuje akcję w symulacji, czyli w zależności od sytuacji pracuje,
   * uczy się, handluje na giełdzie etc.
   */
  public abstract void act();
}
