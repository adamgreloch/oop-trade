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
  private Set<Agent> active;
  private Set<Agent> dead;
  private Stock stock;

  public Simulation(Agent... agents) {
    this.active = new HashSet<>(List.of(agents));
  }

  public static int day() {
    return day;
  }

  public void moveToDead(Agent agent) {
    active.remove(agent);
    dead.add(agent);
  }

  public Stock stock() {
    return stock;
  }
}
