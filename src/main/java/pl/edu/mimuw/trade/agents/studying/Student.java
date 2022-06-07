package pl.edu.mimuw.trade.agents.studying;

import pl.edu.mimuw.trade.agents.Worker;

public class Student implements StudyingStrategy {
  private final int margin;
  private final int period;

  public Student(int margin, int period) {
    this.margin = margin;
    this.period = period;
  }

  public boolean isStudyDay(Worker worker) {
    return false;
  }
}
