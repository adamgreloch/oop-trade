package pl.edu.mimuw.trade.io.adapters;

import com.google.gson.*;
import pl.edu.mimuw.trade.agents.Productivity;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.ProductFactory;

import java.lang.reflect.Type;

public class ProductivityAdapter implements JsonSerializer<Productivity>, JsonDeserializer<Productivity> {
  @Override
  public JsonElement serialize(Productivity productivity, Type typeOfSrc,
                               JsonSerializationContext context) {
    if (productivity == null) return null;
    JsonObject je = new JsonObject();
    for (Product product : ProductFactory.previewProducts())
      je.addProperty(product.productName(), productivity.baseOf(product));

    return je;
  }

  @Override
  public Productivity deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {
    JsonObject jo = (JsonObject) json;

    int food = this.getInt(jo, "jedzenie");
    int diamonds = this.getInt(jo, "diamenty");
    int clothes = this.getInt(jo, "ubrania");
    int tools = this.getInt(jo, "narzedzia");
    int programs = this.getInt(jo, "programy");

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
