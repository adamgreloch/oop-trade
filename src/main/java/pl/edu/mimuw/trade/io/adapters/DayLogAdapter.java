package pl.edu.mimuw.trade.io.adapters;

import com.google.gson.*;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.simulation.DayLog;

import java.lang.reflect.Type;

public class DayLogAdapter implements JsonSerializer<DayLog>, JsonDeserializer<DayLog> {

  @Override
  public JsonElement serialize(DayLog dayLog, Type typeOfSrc,
                               JsonSerializationContext context) {
    if (dayLog == null) return null;
    JsonObject je = new JsonObject();
    je.addProperty("programy", dayLog.getAveragePrice(ProductFactory.program));
    je.addProperty("jedzenie", dayLog.getAveragePrice(ProductFactory.food));
    je.addProperty("ubrania", dayLog.getAveragePrice(ProductFactory.clothes));
    je.addProperty("narzedzia", dayLog.getAveragePrice(ProductFactory.tool));

    return je;
  }

  @Override
  public DayLog deserialize(JsonElement json, Type typeOfT,
                            JsonDeserializationContext context) throws JsonParseException {
    JsonObject jo = (JsonObject) json;
    DayLog fallBack = new DayLog(0, null);
    fallBack.logTransaction(ProductFactory.food, getDouble(jo, "jedzenie"), 1);
    fallBack.logTransaction(ProductFactory.clothes, getDouble(jo, "ubrania"), 1);
    fallBack.logTransaction(ProductFactory.tool, getDouble(jo, "narzedzia"), 1);
    fallBack.logTransaction(ProductFactory.program, getDouble(jo, "programy"), 1);

    return fallBack;
  }

  private double getDouble(final JsonObject wrapper, final String memberName) {
    final JsonElement elem = wrapper.get(memberName);

    if (elem == null) {
      throw new JsonParseException(
              "no '" + memberName + "' member found in json file.");
    }
    return elem.getAsDouble();
  }
}
