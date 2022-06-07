package pl.edu.mimuw.trade.agents.studying;

import pl.edu.mimuw.trade.agents.Worker;

public class Distributive implements StudyingStrategy {
  public Distributive() {
  }

  public boolean isStudyDay(Worker worker) {
    return false;
  }
}
