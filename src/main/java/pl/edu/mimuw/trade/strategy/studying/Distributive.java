package pl.edu.mimuw.trade.strategy.studying;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.simulation.Simulation;

public class Distributive extends StudyingStrategy {

  public Distributive() {
    super("rozkladowy");
  }

  public boolean isStudyDay(Worker worker) {
    double rand = Simulation.RANDOM.nextDouble();
    return rand < 1.0 / (Simulation.day() + 3.0);
  }
}
