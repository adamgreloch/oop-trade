package pl.edu.mimuw.agents.studying;

import pl.edu.mimuw.agents.Worker;

public class Workaholic implements StudyingStrategy {
  public Workaholic() {
  }

  public boolean isStudyDay(Worker worker) {
    return false;
  }
}
