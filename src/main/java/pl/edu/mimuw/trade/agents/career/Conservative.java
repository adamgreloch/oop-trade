package pl.edu.mimuw.trade.agents.career;

import pl.edu.mimuw.trade.agents.Worker;

public class Conservative implements CareerStrategy {
  public boolean isCareerChangePending(Worker worker) {
    return false;
  }

  public Occupation pickCareer(Worker worker) {
    return null;
  }
}
