package pl.edu.mimuw.trade.adapter;

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
    je.addProperty("programy", bag.countPrograms());
    je.addProperty("jedzenie", bag.countFood());
    je.addProperty("ubrania", bag.countClothes());
    je.addProperty("narzedzia", bag.countTools());
    je.addProperty("diamenty", bag.countDiamonds());

    return je;
  }

  @Override
  public Bag deserialize(JsonElement json, Type typeOfT,
                         JsonDeserializationContext context) throws JsonParseException {
    JsonObject jo = (JsonObject) json;
    Bag bag = new Bag();

    bag.storeFood(getInt(jo, "jedzenie"));
    bag.storeDiamonds(getInt(jo, "diamenty"));
    bag.storeProducts(ProductFactory.newClothes(getInt(jo, "ubrania"), 1));
    bag.storeProducts(ProductFactory.newTools(getInt(jo, "narzedzia"), 1));
    bag.storeProducts(ProductFactory.newPrograms(getInt(jo, "programy"), 1));

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
