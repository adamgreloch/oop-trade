package pl.edu.mimuw.trade.io.adapters;

import com.google.gson.*;
import pl.edu.mimuw.trade.agents.Career;
import pl.edu.mimuw.trade.agents.occupations.OccupationFactory;

import java.lang.reflect.Type;

public class CareerAdapter implements JsonSerializer<Career>, JsonDeserializer<Career> {

  @Override
  public JsonElement serialize(Career src, Type typeOfSrc,
                               JsonSerializationContext context) {
    if (src == null)
      return null;
    else {
      Type c = Occupations.map.get(src.current());
      if (c == null)
        throw new RuntimeException("Unknown class: " + src.current());
      return context.serialize(new JsonPrimitive(src.current()));
    }
  }

  public Career deserialize(JsonElement json, Type typeOfT,
                            JsonDeserializationContext context) throws JsonParseException {
    String type = json.getAsString();
    Type c = Occupations.map.get(type);

    if (c == null)
      throw new RuntimeException("Unknown class: " + type);

    return new Career(OccupationFactory.instanceFromType(c), 1);
  }

}
