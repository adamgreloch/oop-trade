package pl.edu.mimuw.trade.strategy.studying;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.strategy.StudyingStrategy;

public class Workaholic extends StudyingStrategy {

  public Workaholic() {
    super("pracus");
  }

  public boolean isStudyDay(Worker worker) {
    return false;
  }
}
