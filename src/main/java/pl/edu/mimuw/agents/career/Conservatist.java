package pl.edu.mimuw.agents.career;

import pl.edu.mimuw.agents.Worker;

public class Conservatist implements CareerStrategy {
  public boolean isCareerChangePending(Worker worker) {
    return false;
  }

  public Occupation pickCareer(Worker worker) {
    return null;
  }
}
