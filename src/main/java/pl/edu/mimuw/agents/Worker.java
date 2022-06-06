package pl.edu.mimuw.agents;

import pl.edu.mimuw.Productivity;
import pl.edu.mimuw.ProductivityVector;
import pl.edu.mimuw.Simulation;
import pl.edu.mimuw.career.Career;
import pl.edu.mimuw.career.Occupation;
import pl.edu.mimuw.products.Bag;
import pl.edu.mimuw.products.WorkerBag;
import pl.edu.mimuw.strategy.CareerStrategy;
import pl.edu.mimuw.strategy.ProductionStrategy;
import pl.edu.mimuw.strategy.PurchaseStrategy;
import pl.edu.mimuw.strategy.StudyingStrategy;

public class Worker extends Agent {
  public static final int DEATH_THRESHOLD = 3;
  public static final int DAILY_CLOTHES_CONSUMPTION = 100;
  public static final int DAILY_FOOD_CONSUMPTION = 100;
  private final Productivity productivity;
  private final WorkerBag bag;
  private final Bag saleBag;
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

    this.bag = new WorkerBag(this);
    this.saleBag = new Bag();

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
    offerPurchase();
    eat();
    bag.wearClothes();
    bag.useAllTools();
    // TODO Zużywa te programy komputerowe, których użył do produkcji w danym dniu.
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
    bag.clear();
  }

  private void offerSale() {
  }

  private void offerPurchase() {
    simulation.stock().hearPurchaseOffers(purchaseStrategy.purchasesToOffer(this));
  }

  private void eat() {
    if (bag.takeFood(DAILY_FOOD_CONSUMPTION) < 0)
      starve();
  }

  public int starvationLevel() {
    return hunger;
  }

  public ProductivityVector getProductivity() {
    return productivity.get();
  }
}
