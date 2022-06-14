package pl.edu.mimuw.trade.agents;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.products.*;
import pl.edu.mimuw.trade.simulation.*;
import pl.edu.mimuw.trade.strategy.*;

import java.util.*;

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
    this.isStudying = false;
    if (this.studyingStrategy.isStudyDay(this))
      this.study();
    else
      this.work();
  }

  public void makeOffers() {
    if (this.isStudying) return;
    Collection<Offer> offers = new HashSet<>();
    Iterator<Product> productsToSell = this.saleBag.iterateThroughLevels();
    while (productsToSell.hasNext()) {
      Tradeable p = (Tradeable) productsToSell.next();
      if (this.saleBag.quantity(p) > 0)
        offers.add(OfferFactory.workerSellOffer(this, p, this.saleBag.quantity(p)));
    }
    offers.addAll(this.purchaseStrategy.purchasesToOffer(this));
    Simulation.stock.addOffer(offers, this);
  }

  public void finishDay() {
    this.eat();
    this.storageBag.useAllTools();
    this.storageBag.wearClothes();
  }

  private void work() {
    this.activateBuffs();
    Product picked = this.productionStrategy.pickToProduce(this);
    int quantity = ProductivityVector.find(this.getProductivity(), picked);
    Set<Product> produced = ProductFactory.produceAlike(picked, quantity, this.productionLevel(picked));
    produced = this.upgradeWithPrograms(produced);
    this.saleBag.storeProducts(produced);
  }

  private Set<Product> upgradeWithPrograms(Set<Product> products) {
    Iterator<Program> ownedPrograms = this.storageBag.programsIterator();
    if (!ownedPrograms.hasNext()) return products;
    Set<Product> res = new HashSet<>();
    Product usedProgram;
    for (Product product : products) {
      if (product instanceof LevelledTradeable && ownedPrograms.hasNext()) {
        usedProgram = ownedPrograms.next();
        res.add(ProductFactory.produceAlike(product, usedProgram.level()));
        this.storageBag.remove(usedProgram);
      } else
        res.add(product);
    }
    return res;
  }

  private void study() {
    if (this.careerStrategy.isCareerChangePending(this))
      this.career.changeOccupation(this.careerStrategy.pickCareer(this));
    else
      this.career.advanceLevel();
    this.hunger = 0;
    this.isStudying = true;
  }

  private void starve() {
    if (this.hunger == DEATH_THRESHOLD)
      this.die();
    else
      this.hunger++;
  }

  private void die() {
    this.storageBag.clear();
    this.isAlive = false;
  }

  private void eat() {
    if (this.storageBag.countFood() < DAILY_FOOD_CONSUMPTION)
      this.starve();
    this.storageBag.takeFood(DAILY_FOOD_CONSUMPTION);
  }

  public int starvationLevel() {
    return this.hunger;
  }

  public ProductivityVector getProductivity() {
    return this.productivity.get();
  }

  public Career getCareer() {
    return this.career;
  }

  private void activateBuffs() {
    this.productivity.clearBuffs();
    this.storageBag.setWorkerOwner(this);
    this.storageBag.listBuffingProducts().forEach(this.productivity::addBuff);
    this.productivity.updateBuffs();
  }

  public int productionLevel(Product product) {
    return this.career.productionLevel(product);
  }

  public int ownedClothes() {
    return this.storageBag.quantity(ProductFactory.clothes);
  }

  public int quantityProduced() {
    return this.saleBag.totalQuantity();
  }

  @Override
  public String toString() {
    return "Worker (Agent " + this.id()
            + ", Food: " + this.storageBag.countFood()
            + ", diamonds: " + this.storageBag.countDiamonds() + ")";
  }
}
