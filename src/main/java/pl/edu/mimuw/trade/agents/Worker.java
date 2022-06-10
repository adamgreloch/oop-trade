package pl.edu.mimuw.trade.agents;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.agents.career.Career;
import pl.edu.mimuw.trade.agents.career.Occupation;
import pl.edu.mimuw.trade.agents.productivity.Productivity;
import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.Tradeable;
import pl.edu.mimuw.trade.simulation.Offer;
import pl.edu.mimuw.trade.simulation.Stock;
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
  private final Productivity productivity;
  @SerializedName(value = "kariera")
  private final Career career;

  @SerializedName("zmiana")
  private final CareerStrategy careerStrategy;
  @SerializedName("kupowanie")
  private final PurchaseStrategy purchaseStrategy;
  @SerializedName("produkcja")
  private final ProductionStrategy productionStrategy;
  @SerializedName("uczenie")
  private final StudyingStrategy studyingStrategy;

  private transient int hunger = 0;

  public Worker(int id, Stock stock, Productivity productivity,
                Occupation occupation,
                int occupationLevel,
                CareerStrategy careerStrategy,
                PurchaseStrategy purchaseStrategy,
                ProductionStrategy productionStrategy,
                StudyingStrategy studyingStrategy) {
    super(id, stock);
    this.productivity = productivity;
    this.career = new Career(occupation, occupationLevel);

    this.storageBag = new Bag();
    this.storageBag.setOwner(this);

    this.careerStrategy = careerStrategy;
    this.purchaseStrategy = purchaseStrategy;
    this.productionStrategy = productionStrategy;
    this.studyingStrategy = studyingStrategy;
  }

  public void act() {
    if (studyingStrategy.isStudyDay(this))
      this.study();
    else
      this.work();
  }

  public void makeOffers() {
    Set<Offer> offers = new HashSet<>();
    Iterator<Product> productsToSell = saleBag.iterateThroughLevels();
    while (productsToSell.hasNext()) {
      Tradeable p = (Tradeable) productsToSell.next();
      if (saleBag.quantity(p) > 0)
        offers.add(new Offer(this, p, saleBag.quantity(p), false));
    }
    offers.addAll(purchaseStrategy.purchasesToOffer(this));
    stock.addOffer(offers, this);
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
    storageBag.listBuffableProducts().forEach(productivity::addBuff);
    productivity.updateBuffs();
  }

  public int productionLevel(Product product) {
    return career.productionLevel(product);
  }

  @Override
  public String toString() {
    return "Worker (Agent " + id() + ", Food: " + storageBag.countFood() + ", diamonds: " + storageBag.countDiamonds() +
            ")";
  }
}
