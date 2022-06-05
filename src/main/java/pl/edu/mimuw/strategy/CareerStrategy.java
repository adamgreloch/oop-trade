package pl.edu.mimuw.strategy;

import pl.edu.mimuw.agents.CareerPath;
import pl.edu.mimuw.agents.Worker;

/**
 * Interfejs strategii zmian ścieżki kariery. Implementować go będą np. Konserwatysta i Rewolucjonista.
 */
public interface CareerStrategy {

  boolean isCareerChangePending(Worker worker);

  CareerPath pickCareerOf(Worker worker);

}
