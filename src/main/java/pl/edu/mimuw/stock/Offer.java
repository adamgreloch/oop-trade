package pl.edu.mimuw.stock;

import pl.edu.mimuw.agents.Agent;
import pl.edu.mimuw.products.TradeableProduct;

/**
 * Oferty kupna i sprzedaży, które będą następnie dopasowywane przez Giełdę.
 */
public class Offer implements Comparable<Offer> {
  private final double price; // price per one
  private final TradeableProduct product;
  boolean isPurchaseOffer;
  boolean isWorkerOffer = false;
  private final Agent issuer; // possibly redund
  private int quantity;
  private boolean highestLevelPossible = false;

  /**
   * Worker's offer constructor.
   */
  public Offer(Agent issuer, TradeableProduct product, int quantity, boolean isPurchaseOffer) {
    this.issuer = issuer;
    this.isWorkerOffer = true;
    this.highestLevelPossible = true;
    this.isPurchaseOffer = isPurchaseOffer;
    this.product = product;
    this.quantity = quantity;
    this.price = 0;
  }

  /**
   * Speculator's offer constructor.
   */
  public Offer(Agent issuer, TradeableProduct product, int quantity, double price, boolean isPurchaseOffer) {
    this.issuer = issuer;
    this.isPurchaseOffer = isPurchaseOffer;
    this.product = product;
    this.quantity = quantity;
    this.price = price;
  }

  /**
   * Construct offer that fully completes provided offer.
   */
  public Offer(Agent issuer, Offer offer, double price) {
    this(issuer, offer.product, offer.quantity, price, true);
    assert !offer.isPurchaseOffer;
  }

  TradeableProduct product() {
    return product;
  }

  public int level() {
    return product.level();
  }

  public double price() {
    return price;
  }

  @Override
  public int compareTo(Offer offer) {
    if (this.isPurchaseOffer && !offer.isPurchaseOffer) return 1;
    if (!this.isPurchaseOffer && offer.isPurchaseOffer) return -1;
    return this.product.tradePriority() - offer.product.tradePriority();
  }

  /**
   * Perform transaction between two offers if they match.
   * Assumes that buyer ({@code buy.issuer}) is financially capable of this
   * transaction.
   *
   * @return Negative integer if this offer has been completed,
   * zero if both have been completed, positive integer if other
   * has been completed.
   */
  public int transaction(Offer other, Log log) {
    assert this.product.equals(other.product);
    assert this.isPurchaseOffer != other.isPurchaseOffer;
    Offer buy = this.isPurchaseOffer ? this : other;
    Offer sell = this.isPurchaseOffer ? other : this;

    int soldQuantity = Math.min(buy.quantity, sell.quantity);

    sell.quantity -= soldQuantity;
    buy.quantity -= soldQuantity;

    double sellPrice = soldQuantity * (buy.isWorkerOffer ? sell.price : buy.price);
    sell.issuer.earnDiamonds(sellPrice);
    buy.issuer.spendDiamonds(sellPrice);
    buy.issuer.acquireProduct(this.product, soldQuantity);

    log.log(this.product, sellPrice, soldQuantity);

    return this.quantity - other.quantity;
  }
}
