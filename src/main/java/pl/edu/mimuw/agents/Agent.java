package pl.edu.mimuw.agents;

import pl.edu.mimuw.bag.Bag;
import pl.edu.mimuw.products.Clothes;
import pl.edu.mimuw.products.Program;
import pl.edu.mimuw.products.Tool;
import pl.edu.mimuw.products.TradeableProduct;
import pl.edu.mimuw.stock.Simulation;

import java.util.Set;

public abstract class Agent {
  private final int id;
  protected Simulation simulation;
  protected Bag saleBag;
  protected Bag storageBag;
  protected boolean isAlive = true;


  // TODO is simulation needed?
  public Agent(Simulation simulation) {
    this.simulation = simulation;
    this.id = simulation.assignId();
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

  public void acquireProducts(TradeableProduct product, int quantity) {
    storageBag.storePurchasedProducts(product, quantity);
  }

  public Set<TradeableProduct> takeProducts(TradeableProduct product, int quantity) {
    return saleBag.takeProducts(product, quantity);
  }

  public int hasQuantity(TradeableProduct product) {
    return storageBag.quantity(product);
  }

  public void giveStartingResources(int food, int clothes, int tools, double diamonds, int programs) {
    storageBag.storeFood(food);
    storageBag.storeDiamonds(diamonds);
    storageBag.storeNewProducts(new Clothes(1), clothes);
    storageBag.storeNewProducts(new Tool(1), tools);
    storageBag.storeNewProducts(new Program(1), programs);
  }

}
