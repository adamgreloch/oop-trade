package pl.edu.mimuw.trade.strategy;

import pl.edu.mimuw.trade.agents.Worker;

public abstract class StudyingStrategy extends Strategy {

  public StudyingStrategy(String name) {
    super(name);
  }

  public abstract boolean isStudyDay(Worker worker);

}
