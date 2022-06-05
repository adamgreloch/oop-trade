package pl.edu.mimuw.agents;

import pl.edu.mimuw.Productivity;
import pl.edu.mimuw.Simulation;
import pl.edu.mimuw.products.WorkerBag;
import pl.edu.mimuw.strategy.*;

import java.util.*;

public class Worker extends Agent {
  public static final int DEATH_THRESHOLD = 3;
  public static final int DAILY_FOOD_CONSUMPTION = 100;

  private int hunger = 0;
  private Productivity productivity;

  private CareerPath careerPath;
  private Map<CareerPath, Integer> careers; // Value indicates advancement level

  private final CareerStrategy careerStrategy;
  private final ConsumptionStrategy consumptionStrategy;
  private final ProductionStrategy productionStrategy;
  private final StudyingStrategy studyingStrategy;

  public Worker(Simulation simulation,
                Productivity productivity,
                CareerPath careerPath,
                CareerStrategy careerStrategy,
                ConsumptionStrategy consumptionStrategy,
                ProductionStrategy productionStrategy,
                StudyingStrategy studyingStrategy) {
    super(simulation);
    this.productivity = productivity;

    this.bag = new WorkerBag(this);

    this.careerPath = careerPath;
    this.careers = new HashMap<>();
    this.careers.put(careerPath, 1);

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
  }

  private void study() {
    if (careerStrategy.isCareerChangePending(this)) {
      CareerPath change = careerStrategy.pickCareerOf(this);
      if (!careers.containsKey(change))
        careers.put(change, 1);
      careerPath = change;
    }


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

  public void eat() {
    if (bag.takeFood(DAILY_FOOD_CONSUMPTION) < 0)
      starve();
  }

  public int starvationLevel() {
    return hunger;
  }
}
