package pl.edu.mimuw.trade.agents;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.agents.career.Career;
import pl.edu.mimuw.trade.agents.productivity.Productivity;
import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.Tradeable;
import pl.edu.mimuw.trade.simulation.Offer;
import pl.edu.mimuw.trade.simulation.OfferFactory;
import pl.edu.mimuw.trade.simulation.Simulation;
import pl.edu.mimuw.trade.strategy.career.CareerStrategy;
import pl.edu.mimuw.trade.strategy.production.ProductionStrategy;
import pl.edu.mimuw.trade.strategy.purchase.PurchaseStrategy;
import pl.edu.mimuw.trade.strategy.studying.StudyingStrategy;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Worker extends Agent {
  public static final int DEATH_THRESHOLD = 3;
  public static final int DAILY_CLOTHES_CONSUMPTION = 100;
  public static final int DAILY_FOOD_CONSUMPTION = 100;

  @SerializedName("produktywnosc")
  private Productivity productivity;
  @SerializedName("kariera")
  private Career career;

  @SerializedName("zmiana")
  private CareerStrategy careerStrategy;
  @SerializedName("kupowanie")
  private PurchaseStrategy purchaseStrategy;
  @SerializedName("produkcja")
  private ProductionStrategy productionStrategy;
  @SerializedName("uczenie")
  private StudyingStrategy studyingStrategy;

  private transient int hunger = 0;
  private transient boolean isStudying;

  public Worker() {
    super();
    this.actionPriority = 0;
  }

  public void act() {
    isStudying = false;
    if (studyingStrategy.isStudyDay(this))
      this.study();
    else
      this.work();
  }

  public void makeOffers() {
    if (isStudying) return;
    Set<Offer> offers = new HashSet<>();
    Iterator<Product> productsToSell = saleBag.iterateThroughLevels();
    while (productsToSell.hasNext()) {
      Tradeable p = (Tradeable) productsToSell.next();
      if (saleBag.quantity(p) > 0)
        offers.add(OfferFactory.workerSellOffer(this, p, saleBag.quantity(p)));
    }
    offers.addAll(purchaseStrategy.purchasesToOffer(this));
    Simulation.stock.addOffer(offers, this);
  }

  public void finishDay() {
    eat();
    storageBag.useAllTools();
    storageBag.wearClothes();
    // TODO Zużywa te programy komputerowe, których użył do produkcji w danym dniu.
  }

  private void work() {
    activateBuffs();
    saleBag.storeProducts(productionStrategy.produce(this));
  }

  private void study() {
    if (careerStrategy.isCareerChangePending(this))
      career.changeOccupation(careerStrategy.pickCareer(this));
    else
      career.advanceLevel();
    hunger = 0;
    isStudying = true;
  }

  private void starve() {
    if (hunger == DEATH_THRESHOLD)
      die();
    else
      hunger++;
  }

  private void die() {
    storageBag.clear();
    isAlive = false;
  }

  private void eat() {
    if (storageBag.countFood() < DAILY_FOOD_CONSUMPTION)
      starve();
    storageBag.takeFood(DAILY_FOOD_CONSUMPTION);
  }

  public int starvationLevel() {
    return hunger;
  }

  public ProductivityVector getProductivity() {
    return productivity.get();
  }

  public Career getCareer() {
    return career;
  }

  private void activateBuffs() {
    productivity.clearBuffs();
    storageBag.setWorkerOwner(this);
    storageBag.listBuffableProducts().forEach(productivity::addBuff);
    productivity.updateBuffs();
  }

  public int productionLevel(Product product) {
    return career.productionLevel(product);
  }

  @Override
  public String toString() {
    return "Worker (Agent " + id()
            + ", Food: " + storageBag.countFood()
            + ", diamonds: " + storageBag.countDiamonds() + ")";
  }
}
