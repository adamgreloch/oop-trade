package pl.edu.mimuw;

import pl.edu.mimuw.agents.Agent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Centrum symulacji. Przeprowadza rundy przez kolejne etapy, przeprowadza symulację przez kolejne rundy
 */
public class Simulation {
  private static int day = 1;
  private Set<Agent> agents;

  public Simulation(Agent... agents) {
    this.agents = new HashSet<>(List.of(agents));
  }

  public static int day() {
    return day;
  }
}
