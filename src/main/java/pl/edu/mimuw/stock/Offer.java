package pl.edu.mimuw.stock;

import pl.edu.mimuw.agents.Agent;
import pl.edu.mimuw.products.TradeableProduct;

/**
 * Oferty kupna i sprzedaży, które będą następnie dopasowywane przez Giełdę.
 */
public class Offer {
  boolean purchaseOffer;
  boolean workerOffer = false;

  private Agent issuer;
  private final double price;
  private final int quantity;
  private final int level;

  private boolean highestLevelPossible = false;

  private final TradeableProduct product;

  /**
   * Worker's offer constructor.
   */
  public Offer(Agent issuer, TradeableProduct product, int quantity, boolean purchaseOffer) {
    this.issuer = issuer;
    this.workerOffer = true;
    this.highestLevelPossible = true;
    this.purchaseOffer = purchaseOffer;
    this.product = product;
    this.quantity = quantity;
    this.price = 0;
    this.level = product.level();
  }

  /**
   * Speculator's offer constructor.
   */
  public Offer(TradeableProduct product, int quantity, double price, boolean purchaseOffer) {
    this.purchaseOffer = purchaseOffer;
    this.product = product;
    this.quantity = quantity;
    this.price = price;
    this.level = product.level();
  }

  TradeableProduct product() {
    return product;
  }

}
