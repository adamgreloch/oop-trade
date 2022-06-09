package pl.edu.mimuw.trade.adapter;

import com.google.gson.*;
import pl.edu.mimuw.trade.strategy.career.CareerStrategy;
import pl.edu.mimuw.trade.strategy.career.Conservative;
import pl.edu.mimuw.trade.strategy.career.Revolutionist;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

public class CareerStrategyAdapter implements JsonSerializer<CareerStrategy> {

  static Map<String, Type> map = new TreeMap<>();

  static {
    map.put("konserwatysta", Conservative.class);
    map.put("rewolucjonista", Revolutionist.class);
  }

  @Override
  public JsonElement serialize(CareerStrategy src, Type typeOfSrc,
                               JsonSerializationContext context) {
    if (src == null)
      return null;
    else {
      Type c = map.get(src.name);
      if (c == null)
        throw new RuntimeException("Unknown class: " + src.name);
      return context.serialize(src, c);
    }
  }

  public CareerStrategy deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {
    String type = json.getAsJsonObject().get("type").getAsString();
    Type c = map.get(type);
    if (c == null)
      throw new RuntimeException("Unknown class: " + type);
    return context.deserialize(json, c);
  }
}
