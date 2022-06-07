package pl.edu.mimuw.trade.agents.studying;

import pl.edu.mimuw.trade.agents.Worker;

public class Economical implements StudyingStrategy {
  private final int diamondThreshold;

  public Economical(int diamondThreshold) {
    this.diamondThreshold = diamondThreshold;
  }

  public boolean isStudyDay(Worker worker) {
    return worker.diamonds() > diamondThreshold;
  }
}
