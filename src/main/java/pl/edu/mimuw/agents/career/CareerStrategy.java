package pl.edu.mimuw.agents.career;

import pl.edu.mimuw.agents.Worker;

/**
 * Interfejs strategii zmian ścieżki kariery. Implementować go będą np. Konserwatysta i Rewolucjonista.
 */
public interface CareerStrategy {

  boolean isCareerChangePending(Worker worker);

  Occupation pickCareer(Worker worker);

}
