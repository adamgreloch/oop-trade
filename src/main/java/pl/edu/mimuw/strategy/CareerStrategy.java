package pl.edu.mimuw.strategy;

import pl.edu.mimuw.agents.Occupation;
import pl.edu.mimuw.agents.Worker;

/**
 * Interfejs strategii zmian ścieżki kariery. Implementować go będą np. Konserwatysta i Rewolucjonista.
 */
public interface CareerStrategy {

  boolean isCareerChangePending(Worker worker);

  Occupation pickCareer(Worker worker);

}
