package pl.edu.mimuw.trade.agents;

import pl.edu.mimuw.trade.agents.career.Career;
import pl.edu.mimuw.trade.agents.career.CareerStrategy;
import pl.edu.mimuw.trade.agents.career.Occupation;
import pl.edu.mimuw.trade.agents.production.ProductionStrategy;
import pl.edu.mimuw.trade.agents.productivity.Productivity;
import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;
import pl.edu.mimuw.trade.agents.purchase.PurchaseStrategy;
import pl.edu.mimuw.trade.agents.studying.StudyingStrategy;
import pl.edu.mimuw.trade.bag.WorkerBag;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.Tradeable;
import pl.edu.mimuw.trade.stock.Offer;
import pl.edu.mimuw.trade.stock.Simulation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Worker extends Agent {
  public static final int DEATH_THRESHOLD = 3;
  public static final int DAILY_CLOTHES_CONSUMPTION = 100;
  public static final int DAILY_FOOD_CONSUMPTION = 100;
  private final Productivity productivity;
  private final WorkerBag workerBag;
  private final Career career;
  private final CareerStrategy careerStrategy;
  private final PurchaseStrategy purchaseStrategy;
  private final ProductionStrategy productionStrategy;
  private final StudyingStrategy studyingStrategy;
  private int hunger = 0;

  public Worker(int id, Simulation simulation, Productivity productivity,
                Occupation occupation,
                int occupationLevel,
                CareerStrategy careerStrategy,
                PurchaseStrategy purchaseStrategy,
                ProductionStrategy productionStrategy,
                StudyingStrategy studyingStrategy) {
    super(id, simulation);
    this.productivity = productivity;
    this.career = new Career(occupation, occupationLevel);
    this.workerBag = new WorkerBag(this);
    this.storageBag = workerBag;
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
    simulation.stock().addOffer(offers, this);
  }

  public void finishDay() {
    eat();
    workerBag.useAllTools();
    workerBag.wearClothes();
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
    workerBag.clear();
    isAlive = false;
  }

  private void eat() {
    if (workerBag.countFood() < DAILY_FOOD_CONSUMPTION)
      starve();
    workerBag.takeFood(DAILY_FOOD_CONSUMPTION);
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
    workerBag.listBuffableProducts().forEach(productivity::addBuff);
    productivity.updateBuffs();
  }

  public int productionLevel(Product product) {
    return career.productionLevel(product);
  }

  @Override
  public String toString() {
    return "Worker (Agent " + id() + ", Food: " + workerBag.countFood() + ", diamonds: " + workerBag.countDiamonds() +
            ")";
  }
}
