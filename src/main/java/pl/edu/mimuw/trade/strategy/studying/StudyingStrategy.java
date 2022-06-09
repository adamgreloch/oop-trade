package pl.edu.mimuw.trade.strategy.studying;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.strategy.Strategy;

public abstract class StudyingStrategy extends Strategy {

  public StudyingStrategy(String name) {
    super(name);
  }

  public abstract boolean isStudyDay(Worker worker);

}
