package pl.edu.mimuw.trade.strategy.studying;

import pl.edu.mimuw.trade.agents.Worker;

public class Periodical extends StudyingStrategy {
  private final int period;

  public Periodical(int period) {
    super("okresowy");
    this.period = period;
  }

  public boolean isStudyDay(Worker worker) {
    return false;
  }
}
