package pl.edu.mimuw.trade.io.adapters;

import com.google.gson.*;
import pl.edu.mimuw.trade.agents.Bag;
import pl.edu.mimuw.trade.products.ProductFactory;

import java.lang.reflect.Type;

public class BagAdapter implements JsonSerializer<Bag>, JsonDeserializer<Bag> {
  @Override
  public JsonElement serialize(Bag bag, Type typeOfSrc,
                               JsonSerializationContext context) {
    if (bag == null) return null;
    JsonObject je = new JsonObject();
    je.add("programy", bag.perLevelPrograms());
    je.addProperty("jedzenie", bag.countFood());
    je.add("ubrania", bag.perLevelClothes());
    je.add("narzedzia", bag.perLevelTools());
    je.addProperty("diamenty", bag.countDiamonds());

    return je;
  }

  @Override
  public Bag deserialize(JsonElement json, Type typeOfT,
                         JsonDeserializationContext context) throws JsonParseException {
    JsonObject jo = (JsonObject) json;
    Bag bag = new Bag();

    bag.storeFood(this.getInt(jo, "jedzenie"));
    bag.storeDiamonds(this.getInt(jo, "diamenty"));
    bag.storeProducts(ProductFactory.newClothes(this.getInt(jo, "ubrania"), 1));
    bag.storeProducts(ProductFactory.newTools(this.getInt(jo, "narzedzia"), 1));
    bag.storeProducts(ProductFactory.newPrograms(this.getInt(jo, "programy"), 1));

    return bag;
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
