package pl.edu.mimuw.products;

public class ProductFactory {
  private static final TradeableProduct[] tradeable = {new Food(), new Clothes(1), new Tool(1), new Program(1)};
  private static final Product[] products = {new Food(), new Clothes(1), new Tool(1), new Diamond(), new Program(1)};

  public static Product[] previewProducts() {
    return products.clone();
  }

  public static TradeableProduct[] previewTradeable() {
    return tradeable.clone();
  }
}
