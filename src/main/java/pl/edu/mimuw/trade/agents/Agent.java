package pl.edu.mimuw.trade.agents;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.products.Tradeable;
import pl.edu.mimuw.trade.simulation.Stock;

import java.util.Set;

public abstract class Agent {

  @SerializedName("id")
  private final int id;

  protected transient Stock stock;
  protected transient Bag saleBag;

  @SerializedName("zasoby")
  protected Bag storageBag;

  protected transient boolean isAlive = true;

  public Agent(int id, Stock stock) {
    this.stock = stock;
    this.id = id;
    this.saleBag = new Bag();
    this.storageBag = saleBag;
  }

  /**
   * Dokonuje akcję w symulacji, czyli w zależności od sytuacji pracuje,
   * uczy się, handluje na giełdzie etc.
   */
  public abstract void act();

  public abstract void makeOffers();

  public abstract void finishDay();

  public int id() {
    return id;
  }

  public double diamonds() {
    return storageBag.countDiamonds();
  }

  public boolean isDead() {
    return !isAlive;
  }

  public void earnDiamonds(double amount) {
    storageBag.storeDiamonds(amount);
  }

  public void spendDiamonds(double amount) {
    storageBag.takeDiamonds(amount);
  }

  public void acquireProducts(Set<Product> products) {
    storageBag.storeProducts(products);
  }

  public Set<Product> takeProducts(Product product, int quantity) {
    return saleBag.takeProducts(product, quantity);
  }

  public int hasQuantity(Tradeable product) {
    return storageBag.quantity(product);
  }

  public void giveStartingResources(int food, int clothes, int tools, double diamonds, int programs) {
    storageBag.storeFood(food);
    storageBag.storeDiamonds(diamonds);
    storageBag.storeProducts(ProductFactory.newClothes(clothes, 1));
    storageBag.storeProducts(ProductFactory.newTools(tools, 1));
    storageBag.storeProducts(ProductFactory.newPrograms(programs, 1));
  }


}
