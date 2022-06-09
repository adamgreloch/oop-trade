package pl.edu.mimuw.trade.strategy.studying;

import pl.edu.mimuw.trade.agents.Worker;

public class Student extends StudyingStrategy {
  private final int margin;
  private final int period;

  public Student(int margin, int period) {
    super("student");
    this.margin = margin;
    this.period = period;
  }

  public boolean isStudyDay(Worker worker) {
    return false;
  }
}
