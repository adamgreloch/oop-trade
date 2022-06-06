package pl.edu.mimuw.stock;

import pl.edu.mimuw.agents.Agent;
import pl.edu.mimuw.products.TradeableProduct;

/**
 * Oferty kupna i sprzedaży, które będą następnie dopasowywane przez Giełdę.
 */
public class Offer implements Comparable<Offer> {
  boolean isPurchaseOffer;
  boolean isWorkerOffer = false;

  private Agent issuer; // possibly redund
  private final double price; // price per one
  private int quantity;

  private boolean highestLevelPossible = false;

  private final TradeableProduct product;

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
  public int transaction(Offer other) {
    assert this.product.equals(other.product);
    assert this.isPurchaseOffer != other.isPurchaseOffer;
    Offer buy = this.isPurchaseOffer ? this : other;
    Offer sell = this.isPurchaseOffer ? other : this;

    int sold = Math.min(buy.quantity, sell.quantity);

    sell.quantity -= sold;
    buy.quantity -= sold;

    double total = sold * (buy.isWorkerOffer ? sell.price : buy.price);
    sell.issuer.earnDiamonds(total);
    buy.issuer.spendDiamonds(total);

    return this.quantity - other.quantity;
  }
}
