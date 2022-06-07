package pl.edu.mimuw.trade.agents.studying;

import pl.edu.mimuw.trade.agents.Worker;

public class Workaholic implements StudyingStrategy {
  public Workaholic() {
  }

  public boolean isStudyDay(Worker worker) {
    return false;
  }
}
