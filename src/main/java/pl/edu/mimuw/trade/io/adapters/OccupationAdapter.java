package pl.edu.mimuw.trade.io.adapters;

import com.google.gson.*;
import pl.edu.mimuw.trade.agents.career.*;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

public class OccupationAdapter implements JsonSerializer<Occupation>, JsonDeserializer<Occupation> {

  static Map<String, Type> map = new TreeMap<>();

  static {
    map.put("rolnik", Farmer.class);
    map.put("rzemieslnik", Craftsman.class);
    map.put("inzynier", Engineer.class);
    map.put("gornik", Miner.class);
    map.put("programista", Programmer.class);
  }

  @Override
  public JsonElement serialize(Occupation src, Type typeOfSrc,
                               JsonSerializationContext context) {
    if (src == null)
      return null;
    else {
      Type c = map.get(src.name);
      if (c == null)
        throw new RuntimeException("Unknown class: " + src.name);
      return context.serialize(new JsonPrimitive(src.name));
    }
  }

  @Override
  public Occupation deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {
    String type = json.getAsString();
    Type c = map.get(type);
    if (c == null)
      throw new RuntimeException("Unknown class: " + type);
    return context.deserialize(json, c);
  }

}
