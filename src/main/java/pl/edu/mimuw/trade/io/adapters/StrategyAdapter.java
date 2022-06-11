package pl.edu.mimuw.trade.io.adapters;

import com.google.gson.*;
import pl.edu.mimuw.trade.strategy.Strategy;
import pl.edu.mimuw.trade.strategy.career.Conservative;
import pl.edu.mimuw.trade.strategy.career.Revolutionist;
import pl.edu.mimuw.trade.strategy.production.*;
import pl.edu.mimuw.trade.strategy.purchase.Gadgeteer;
import pl.edu.mimuw.trade.strategy.purchase.Mechanized;
import pl.edu.mimuw.trade.strategy.purchase.Stickler;
import pl.edu.mimuw.trade.strategy.purchase.Technophobe;
import pl.edu.mimuw.trade.strategy.speculation.AverageSpeculation;
import pl.edu.mimuw.trade.strategy.stock.Balanced;
import pl.edu.mimuw.trade.strategy.stock.Capitalist;
import pl.edu.mimuw.trade.strategy.stock.Socialist;
import pl.edu.mimuw.trade.strategy.studying.*;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

public class StrategyAdapter<T extends Strategy> implements JsonSerializer<T>, JsonDeserializer<T> {

  static Map<String, Type> map = new TreeMap<>();

  static {
    map.put("konserwatysta", Conservative.class);
    map.put("rewolucjonista", Revolutionist.class);

    map.put("rozkladowy", Distributive.class);
    map.put("oszczedny", Economical.class);
    map.put("okresowy", Periodical.class);
    map.put("student", Student.class);
    map.put("pracus", Workaholic.class);

    map.put("gadzeciarz", Gadgeteer.class);
    map.put("zmechanizowany", Mechanized.class);
    map.put("czyscioszek", Stickler.class);
    map.put("technofob", Technophobe.class);

    map.put("sredniak", AverageProduction.class);
    map.put("zachlanny", Greedy.class);
    map.put("perspektywiczny", Prospective.class);
    map.put("losowy", Random.class);
    map.put("krotkowzroczny", Shortsighted.class);

    map.put("sredni", AverageSpeculation.class);

    map.put("kapitalistyczna", Capitalist.class);
    map.put("socjalistyczna", Socialist.class);
    map.put("zrownowazona", Balanced.class);
  }

  @Override
  public JsonElement serialize(T src, Type typeOfSrc,
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

  @Override
  public T deserialize(JsonElement json, Type typeOfT,
                       JsonDeserializationContext context) throws JsonParseException {
    String type;
    if (json.isJsonObject())
      type = json.getAsJsonObject().get("typ").getAsString();
    else {
      type = json.getAsString();
      JsonObject jsonObj = new JsonObject();
      jsonObj.addProperty("typ", type);
      json = jsonObj;
    }
    Type c = map.get(type);
    if (c == null)
      throw new RuntimeException("Unknown class: " + type);
    return context.deserialize(json, c);
  }
}
