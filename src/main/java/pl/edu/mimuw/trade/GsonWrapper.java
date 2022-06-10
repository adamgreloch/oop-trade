package pl.edu.mimuw.trade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.edu.mimuw.trade.adapter.*;
import pl.edu.mimuw.trade.agents.Bag;
import pl.edu.mimuw.trade.agents.career.Career;
import pl.edu.mimuw.trade.agents.career.Occupation;
import pl.edu.mimuw.trade.agents.productivity.Productivity;
import pl.edu.mimuw.trade.simulation.DayLog;
import pl.edu.mimuw.trade.strategy.career.CareerStrategy;
import pl.edu.mimuw.trade.strategy.production.ProductionStrategy;
import pl.edu.mimuw.trade.strategy.purchase.PurchaseStrategy;
import pl.edu.mimuw.trade.strategy.speculation.SpeculationStrategy;
import pl.edu.mimuw.trade.strategy.stock.StockStrategy;
import pl.edu.mimuw.trade.strategy.studying.StudyingStrategy;

import java.io.Reader;

public class GsonWrapper {
  private static final GsonBuilder gsonBuilder = new GsonBuilder();

  private static final Gson gson = gsonBuilder.registerTypeAdapter(CareerStrategy.class, new StrategyAdapter<>())
          .registerTypeAdapter(ProductionStrategy.class, new StrategyAdapter<>())
          .registerTypeAdapter(PurchaseStrategy.class, new StrategyAdapter<>())
          .registerTypeAdapter(SpeculationStrategy.class, new StrategyAdapter<>())
          .registerTypeAdapter(StockStrategy.class, new StrategyAdapter<>())
          .registerTypeAdapter(StudyingStrategy.class, new StrategyAdapter<>())
          .registerTypeAdapter(Occupation.class, new OccupationAdapter())
          .registerTypeAdapter(Career.class, new CareerAdapter())
          .registerTypeAdapter(Bag.class, new BagAdapter())
          .registerTypeAdapter(Productivity.class, new ProductivityAdapter())
          .registerTypeAdapter(DayLog.class, new DayLogAdapter())
          .setPrettyPrinting().create();

  public static String toJson(Object src) {
    return gson.toJson(src);
  }

  public static <T> T fromJson(String json, Class<T> classOfT) {
    return gson.fromJson(json, classOfT);
  }
  public static <T> T fromJson(Reader json, Class<T> classOfT) {
    return gson.fromJson(json, classOfT);
  }

}
