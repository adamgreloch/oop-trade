package pl.edu.mimuw.stock;

import pl.edu.mimuw.products.TradeableProduct;

/**
 * Oferty kupna i sprzedaży, które będą następnie dopasowywane przez Giełdę.
 */
public class Offer {
  boolean isPurchaseOffer;
  private boolean workerOffer = false;

  private double price;
  private int quantity;
  private int level;

  private boolean highestLevelPossible = false;

  private TradeableProduct product;

  /**
   * Worker's offer constructor.
   */
  public Offer(TradeableProduct product, int quantity, boolean isPurchaseOffer) {
    this.workerOffer = true;
    this.highestLevelPossible = true;
    this.isPurchaseOffer = isPurchaseOffer;
    this.product = product;
    this.quantity = quantity;
    this.price = 0;
    this.level = product.level();
  }

  /**
   * Speculator's offer constructor.
   */
  public Offer(TradeableProduct product, int quantity, double price, boolean isPurchaseOffer) {
    this.isPurchaseOffer = isPurchaseOffer;
    this.product = product;
    this.quantity = quantity;
    this.price = price;
    this.level = product.level();
  }

  TradeableProduct product() {
    return product;
  }

}
