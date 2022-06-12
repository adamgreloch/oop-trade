package pl.edu.mimuw.trade.strategy.studying;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.simulation.StockAnalysis;

public class Student extends StudyingStrategy {
  private static final int FOOD_AMOUNT = 100;

  @SerializedName("zapas")
  private int margin;
  @SerializedName("okres")
  private int period;

  public Student() {
    super("student");
  }

  public boolean isStudyDay(Worker worker) {
    return FOOD_AMOUNT * this.margin * StockAnalysis.avgPrice(ProductFactory.food, this.period) >= worker.diamonds();
  }
}
