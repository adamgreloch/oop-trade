package pl.edu.mimuw.trade.agents.studying;

import pl.edu.mimuw.trade.agents.Worker;

public class Periodical implements StudyingStrategy {
  private final int period;

  public Periodical(int period) {
    this.period = period;
  }

  public boolean isStudyDay(Worker worker) {
    return false;
  }
}
