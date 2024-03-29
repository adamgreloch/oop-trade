package pl.edu.mimuw.trade.strategy.studying;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.simulation.Simulation;
import pl.edu.mimuw.trade.strategy.StudyingStrategy;

public class Periodical extends StudyingStrategy {

  @SerializedName("okresowosc_nauki")
  private int period;

  public Periodical() {
    super("okresowy");
  }

  public boolean isStudyDay(Worker worker) {
    return Simulation.day() % this.period == 0;
  }
}
