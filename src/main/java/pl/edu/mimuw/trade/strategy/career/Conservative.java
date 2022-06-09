package pl.edu.mimuw.trade.strategy.career;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.agents.career.Occupation;

public class Conservative extends CareerStrategy {

  public Conservative() {
    super("konserwatysta");
  }

  public boolean isCareerChangePending(Worker worker) {
    return false;
  }

  public Occupation pickCareer(Worker worker) {
    return null;
  }
}
