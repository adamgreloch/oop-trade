package pl.edu.mimuw.trade.strategy;

import pl.edu.mimuw.trade.agents.Occupation;
import pl.edu.mimuw.trade.agents.Worker;

/**
 * Interfejs strategii zmian ścieżki kariery. Implementować go będą np. Konserwatysta i Rewolucjonista.
 */
public abstract class CareerStrategy extends Strategy {

  public CareerStrategy(String name) {
    super(name);
  }

  public abstract boolean isCareerChangePending(Worker worker);

  public abstract Occupation pickCareer(Worker worker);
}
