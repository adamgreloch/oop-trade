package pl.edu.mimuw.agents;

import pl.edu.mimuw.Simulation;
import pl.edu.mimuw.agents.career.Career;
import pl.edu.mimuw.agents.career.CareerStrategy;
import pl.edu.mimuw.agents.career.Occupation;
import pl.edu.mimuw.agents.production.ProductionStrategy;
import pl.edu.mimuw.agents.productivity.Productivity;
import pl.edu.mimuw.agents.productivity.ProductivityVector;
import pl.edu.mimuw.agents.purchase.PurchaseStrategy;
import pl.edu.mimuw.agents.studying.StudyingStrategy;
import pl.edu.mimuw.bag.WorkerBag;
import pl.edu.mimuw.products.*;
import pl.edu.mimuw.stock.Offer;

import java.util.HashSet;
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

  public Worker(Simulation simulation,
                Productivity productivity,
                Occupation occupation,
                CareerStrategy careerStrategy,
                PurchaseStrategy purchaseStrategy,
                ProductionStrategy productionStrategy,
                StudyingStrategy studyingStrategy) {
    super(simulation);
    this.productivity = productivity;

    this.career = new Career(occupation);

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

  private void work() {
    productionStrategy.produce(this, saleBag);
    offerSale();
    eat();
    workerBag.useAllTools();
    workerBag.wearClothes();
    // TODO Zużywa te programy komputerowe, których użył do produkcji w danym dniu.
    offerPurchase();
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
    simulation.moveToDead(this);
  }

  private void offerSale() {
    Set<Offer> offers = new HashSet<>();
    TradeableProduct toSell;
    for (Product p : saleBag)
      if (p instanceof TradeableProduct) {
        toSell = (TradeableProduct) p;
        offers.add(new Offer(this, toSell, saleBag.countTradeable(toSell), false));
      }
    simulation.stock().addOffer(offers, this);
  }

  private void offerPurchase() {
    simulation.stock().addOffer(purchaseStrategy.purchasesToOffer(this), this);
  }

  private void eat() {
    if (workerBag.takeFood(DAILY_FOOD_CONSUMPTION) < 0)
      starve();
  }

  public void giveStartingResources(int food, int clothes, int tools, int diamonds, int programs) {
    workerBag.storeFood(food);
    workerBag.storeDiamonds(diamonds);
    workerBag.storeProduct(new Clothes(1), clothes);
    workerBag.storeProduct(new Tool(1), tools);
    workerBag.storeProduct(new Program(1), programs);
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

  @Override
  public String toString() {
    return "Worker " + id();
  }
}
