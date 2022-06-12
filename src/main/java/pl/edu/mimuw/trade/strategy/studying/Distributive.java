package pl.edu.mimuw.trade.strategy.studying;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.simulation.Simulation;

public class Distributive extends StudyingStrategy {

  public static final double PARAMETER = 3.0;

  public Distributive() {
    super("rozkladowy");
  }

  public boolean isStudyDay(Worker worker) {
    double rand = Simulation.RANDOM.nextDouble();
    return rand < 1.0 / (Simulation.day() + PARAMETER);
  }
}
