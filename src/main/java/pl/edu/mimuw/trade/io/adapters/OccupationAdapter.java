package pl.edu.mimuw.trade.io.adapters;

import com.google.gson.*;
import pl.edu.mimuw.trade.agents.Occupation;

import java.lang.reflect.Type;

public class OccupationAdapter implements JsonSerializer<Occupation>, JsonDeserializer<Occupation> {

  @Override
  public JsonElement serialize(Occupation src, Type typeOfSrc,
                               JsonSerializationContext context) {
    if (src == null)
      return null;
    else {
      Type c = Occupations.map.get(src.name);
      if (c == null)
        throw new RuntimeException("Unknown class: " + src.name);
      return context.serialize(new JsonPrimitive(src.name));
    }
  }

  @Override
  public Occupation deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {
    String type = json.getAsString();
    Type c = Occupations.map.get(type);
    if (c == null)
      throw new RuntimeException("Unknown class: " + type);
    return context.deserialize(json, c);
  }

}
