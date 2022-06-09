package pl.edu.mimuw.trade.strategy.career;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.agents.career.Occupation;
import pl.edu.mimuw.trade.strategy.Strategy;

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
