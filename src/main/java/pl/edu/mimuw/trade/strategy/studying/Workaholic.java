package pl.edu.mimuw.trade.strategy.studying;

import pl.edu.mimuw.trade.agents.Worker;

public class Workaholic extends StudyingStrategy {

  public Workaholic() {
    super("pracus");
  }

  public boolean isStudyDay(Worker worker) {
    return false;
  }
}
