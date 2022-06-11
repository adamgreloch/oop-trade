package pl.edu.mimuw.trade.io.adapters;

import com.google.gson.*;
import pl.edu.mimuw.trade.agents.productivity.Productivity;

import java.lang.reflect.Type;

public class ProductivityAdapter implements JsonSerializer<Productivity>, JsonDeserializer<Productivity> {
  @Override
  public JsonElement serialize(Productivity productivity, Type typeOfSrc,
                               JsonSerializationContext context) {
    if (productivity == null) return null;
    JsonObject je = new JsonObject();
    je.addProperty("programy", productivity.programsBase());
    je.addProperty("jedzenie", productivity.foodBase());
    je.addProperty("ubrania", productivity.clothesBase());
    je.addProperty("narzedzia", productivity.toolsBase());
    je.addProperty("diamenty", productivity.diamondsBase());

    return je;
  }

  @Override
  public Productivity deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {
    JsonObject jo = (JsonObject) json;

    int food = getInt(jo, "jedzenie");
    int diamonds = getInt(jo, "diamenty");
    int clothes = getInt(jo, "ubrania");
    int tools = getInt(jo, "narzedzia");
    int programs = getInt(jo, "programy");

    return new Productivity(food, diamonds, clothes, tools, programs);
  }

  private int getInt(final JsonObject wrapper, final String memberName) {
    final JsonElement elem = wrapper.get(memberName);

    if (elem == null) {
      throw new JsonParseException(
              "no '" + memberName + "' member found in json file.");
    }
    return elem.getAsInt();
  }
}
