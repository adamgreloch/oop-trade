package pl.edu.mimuw.agents;

import pl.edu.mimuw.Productivity;
import pl.edu.mimuw.ProductivityVector;
import pl.edu.mimuw.Simulation;
import pl.edu.mimuw.products.Bag;
import pl.edu.mimuw.products.WorkerBag;
import pl.edu.mimuw.strategy.*;

public class Worker extends Agent {
  public static final int DEATH_THRESHOLD = 3;
  public static final int DAILY_CLOTHES_CONSUMPTION = 100;
  public static final int DAILY_FOOD_CONSUMPTION = 100;

  private int hunger = 0;
  private Productivity productivity;

  private final WorkerBag bag;
  private final Bag saleBag;
  private final Career career;

  private final CareerStrategy careerStrategy;
  private final ConsumptionStrategy consumptionStrategy;
  private final ProductionStrategy productionStrategy;
  private final StudyingStrategy studyingStrategy;

  public Worker(Simulation simulation,
                Productivity productivity,
                Occupation occupation,
                CareerStrategy careerStrategy,
                ConsumptionStrategy consumptionStrategy,
                ProductionStrategy productionStrategy,
                StudyingStrategy studyingStrategy) {
    super(simulation);
    this.productivity = productivity;

    this.career = new Career(occupation);

    this.bag = new WorkerBag(this);
    this.saleBag = new Bag();

    this.careerStrategy = careerStrategy;
    this.consumptionStrategy = consumptionStrategy;
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
    sell();
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

  private void sell() {
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
