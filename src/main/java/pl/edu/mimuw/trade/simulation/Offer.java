package pl.edu.mimuw.trade.simulation;

import pl.edu.mimuw.trade.agents.Agent;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.Tradeable;

import java.util.Set;

import static pl.edu.mimuw.trade.simulation.OfferType.BUY;
import static pl.edu.mimuw.trade.simulation.OfferType.SELL;

/**
 * Oferty kupna i sprzedaży, które będą następnie dopasowywane przez Giełdę.
 */
public class Offer implements Comparable<Offer> {

  final OfferType offerType;
  private final double price; // price per one
  private final Tradeable product;
  private final Agent issuer; // possibly redund
  boolean isWorkerOffer;
  private int quantity;
  private boolean isCompleted = false;


  private Offer(Agent issuer, Tradeable product, int quantity,
                double price, boolean isWorkerOffer, boolean isPurchaseOffer) {
    this.issuer = issuer;
    this.offerType = isPurchaseOffer ? BUY : SELL;
    this.isWorkerOffer = isWorkerOffer;
    this.product = product;
    this.quantity = quantity;
    this.price = price;
  }

  // TODO offer factory

  // TODO uwzgledniac zdolnosc finansowa robotnika!!

  /**
   * Worker's offer constructor.
   */
  public Offer(Agent issuer, Tradeable product, int quantity, boolean isPurchaseOffer) {
    this(issuer, product, quantity, 0, true, isPurchaseOffer);
  }

  /**
   * Speculator's offer constructor.
   */
  public Offer(Agent issuer, Tradeable product, int quantity, double price, boolean isPurchaseOffer) {
    this(issuer, product, quantity, price, false, isPurchaseOffer);
  }

  /**
   * Construct offer that fully completes provided offer.
   */
  public Offer(Agent issuer, Offer offer, double price) {
    this(issuer, offer.product, offer.quantity, price, true);
    assert offer.offerType == SELL;
  }

  Tradeable product() {
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
    int cmp = this.offerType.compareTo(offer.offerType);
    if (cmp == 0)
      return this.product.tradePriority() - offer.product.tradePriority();
    else
      return cmp;
  }

  /**
   * Perform transaction between two offers if they match.
   * Assumes that buyer ({@code buy.issuer}) is financially capable of this
   * transaction.
   */
  public void transaction(Offer other, StockLog log) {
    assert this.matches(other);
    Offer buy = this.offerType == BUY ? this : other;
    Offer sell = this.offerType == BUY ? other : this;

    int soldQuantity = Math.min(buy.quantity, sell.quantity);

    sell.quantity -= soldQuantity;
    buy.quantity -= soldQuantity;

    double sellPrice = buy.isWorkerOffer ? sell.price : buy.price;
    double total = sellPrice * soldQuantity;
    sell.issuer.earnDiamonds(total);
    buy.issuer.spendDiamonds(total);
    Set<Product> soldProducts = sell.issuer.takeProducts(this.product, soldQuantity);
    buy.issuer.acquireProducts(soldProducts);

    log.log(this.product, sellPrice, soldQuantity);

    int res = this.quantity - other.quantity;
    if (res <= 0) this.isCompleted = true;
    if (res >= 0) other.isCompleted = true;
  }

  public boolean matches(Offer other) {
    if (this.offerType == BUY && this.issuer.diamonds() < other.price) return false;
    if (other.offerType == BUY && other.issuer.diamonds() < this.price) return false;
    if (this.isWorkerOffer == other.isWorkerOffer) return false;
    if (this.product.level() != other.product.level()) return false;
    return this.product.generalize().equals(other.product);
  }

  public boolean isCompleted() {
    return isCompleted;
  }
}