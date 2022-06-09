package pl.edu.mimuw.trade.strategy.studying;

import pl.edu.mimuw.trade.agents.Worker;

public class Distributive extends StudyingStrategy {

  public Distributive() {
    super("rozkladowy");
  }

  public boolean isStudyDay(Worker worker) {
    return false;
  }
}
