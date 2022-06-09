package pl.edu.mimuw.trade.strategy.studying;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.agents.Worker;

public class Economical extends StudyingStrategy {

  @SerializedName("limit_diamentow")
  private final int diamondThreshold;

  public Economical(int diamondThreshold) {
    super("oszczedny");
    this.diamondThreshold = diamondThreshold;
  }

  public boolean isStudyDay(Worker worker) {
    return worker.diamonds() > diamondThreshold;
  }
}
