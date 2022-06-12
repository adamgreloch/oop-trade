package pl.edu.mimuw.trade.agents;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.Tradeable;

import java.util.Set;

public abstract class Agent implements Comparable<Agent> {

  @SerializedName("zasoby")
  protected Bag storageBag;
  protected transient Bag saleBag;
  protected transient boolean isAlive = true;
  protected transient int actionPriority;
  @SerializedName("id")
  private int id;

  public Agent() {
    this.storageBag = new Bag();
    this.saleBag = new Bag();
  }

  public Agent(int id) {
    this();
    this.id = id;
  }

  /**
   * Dokonuje akcję w symulacji, czyli w zależności od sytuacji pracuje,
   * uczy się, handluje na giełdzie etc.
   */
  public abstract void act();

  public abstract void makeOffers();

  public abstract void finishDay();

  public int id() {
    return this.id;
  }

  public double diamonds() {
    return this.storageBag.countDiamonds();
  }

  public boolean isDead() {
    return !this.isAlive;
  }

  public void earnDiamonds(double amount) {
    this.storageBag.storeDiamonds(amount);
  }

  public void spendDiamonds(double amount) {
    this.storageBag.takeDiamonds(amount);
  }

  public void acquireProducts(Iterable<Product> products) {
    this.storageBag.storeProducts(products);
  }

  public Set<Product> takeOutProduct(Product product, int quantity) {
    return this.saleBag.takeProducts(product, quantity);
  }

  public int ownsQuantity(Tradeable product) {
    return this.storageBag.quantity(product);
  }

  @Override
  public int compareTo(Agent agent) {
    int cmp = this.actionPriority - agent.actionPriority;
    if (cmp == 0)
      return this.id - agent.id;
    else return cmp;
  }
}
