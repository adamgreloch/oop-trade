package pl.edu.mimuw.trade.io;

import com.google.gson.*;
import pl.edu.mimuw.trade.agents.*;
import pl.edu.mimuw.trade.io.adapters.*;
import pl.edu.mimuw.trade.simulation.DayLog;
import pl.edu.mimuw.trade.strategy.*;

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
          .addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
              return f.getAnnotation(SkipSerialization.class) != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
              return false;
            }
          })
          .setPrettyPrinting().create();

  public static String toJson(Object src) {
    return gson.toJson(src);
  }

  public static JsonElement toJsonTree(Object src) {
    return gson.toJsonTree(src);
  }

  public static <T> T fromJson(Reader json, Class<T> classOfT) {
    return gson.fromJson(json, classOfT);
  }

}
