package pl.edu.mimuw.trade;

import com.google.gson.*;
import pl.edu.mimuw.trade.strategy.Strategy;

import java.lang.reflect.Type;

public class InterfaceAdapter
        implements JsonSerializer<Strategy>, JsonDeserializer<Strategy> {

  @Override
  public final JsonElement serialize(final Strategy object, final Type interfaceType,
                                     final JsonSerializationContext context) {
    final JsonObject member = new JsonObject();

    member.addProperty("type", object.getClass().getName());

    member.add("data", context.serialize(object));

    return member;
  }

  @Override
  public final Strategy deserialize(final JsonElement elem, final Type interfaceType,
                                    final JsonDeserializationContext context)
          throws JsonParseException {
    final JsonObject member = (JsonObject) elem;
    final JsonElement typeString = get(member, "type");
    final JsonElement data = get(member, "data");
    final Type actualType = typeForName(typeString);

    return context.deserialize(data, actualType);
  }

  private Type typeForName(final JsonElement typeElem) {
    try {
      return Class.forName(typeElem.getAsString());
    } catch (ClassNotFoundException e) {
      throw new JsonParseException(e);
    }
  }

  private JsonElement get(final JsonObject wrapper, final String memberName) {
    final JsonElement elem = wrapper.get(memberName);

    if (elem == null) {
      throw new JsonParseException(
              "no '" + memberName + "' member found in json file.");
    }
    return elem;
  }

}
