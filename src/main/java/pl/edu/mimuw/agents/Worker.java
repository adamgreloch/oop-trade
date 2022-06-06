package pl.edu.mimuw.agents;

import pl.edu.mimuw.agents.production.ProductionStrategy;
import pl.edu.mimuw.agents.purchase.PurchaseStrategy;
import pl.edu.mimuw.agents.studying.StudyingStrategy;
import pl.edu.mimuw.agents.productivity.Productivity;
import pl.edu.mimuw.agents.productivity.ProductivityVector;
import pl.edu.mimuw.Simulation;
import pl.edu.mimuw.agents.career.Career;
import pl.edu.mimuw.agents.career.Occupation;
import pl.edu.mimuw.products.WorkerBag;
import pl.edu.mimuw.agents.career.CareerStrategy;

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
    offerPurchase();
    eat();
    workerBag.wearClothes();
    workerBag.useAllTools();
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
    workerBag.clear();
  }

  private void offerSale() {
  }

  private void offerPurchase() {
    simulation.stock().addOffer(purchaseStrategy.purchasesToOffer(this), this);
  }

  private void eat() {
    if (workerBag.takeFood(DAILY_FOOD_CONSUMPTION) < 0)
      starve();
  }

  public int starvationLevel() {
    return hunger;
  }

  public ProductivityVector getProductivity() {
    return productivity.get();
  }
}
