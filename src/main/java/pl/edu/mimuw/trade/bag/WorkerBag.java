package pl.edu.mimuw.trade.bag;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.agents.productivity.ProductivityBuff;
import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;
import pl.edu.mimuw.trade.products.Clothes;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.Tool;

import java.util.Collections;
import java.util.List;

public class WorkerBag extends Bag implements ProductivityBuff {
  private static final int MINOR_STARVATION_PENALTY = -100;
  private static final int MAJOR_STARVATION_PENALTY = -300;

  private final Worker owner;

  public WorkerBag(Worker owner) {
    super();
    this.owner = owner;
  }

  public ProductivityVector getBuffValue() {
    assert owner.starvationLevel() < Worker.DEATH_THRESHOLD;

    switch (owner.starvationLevel()) {
      case 1:
        return new ProductivityVector(MINOR_STARVATION_PENALTY);
      case 2:
        return new ProductivityVector(MAJOR_STARVATION_PENALTY);
      default:
        return new ProductivityVector();
    }
  }

  public void wearClothes() {
    int toWear = Worker.DAILY_CLOTHES_CONSUMPTION;
    List<Clothes> notWorn = listClothes();
    if (notWorn == null) return;
    if (notWorn.size() <= toWear) {
      findProduct(new Clothes(1)).clear();
      return;
    }
    Collections.shuffle(notWorn);
    for (Clothes clothes : notWorn) {
      if (toWear == 0) break;
      wearWhileCheckingCondition(clothes);
      toWear--;
    }
  }

  private void wearWhileCheckingCondition(Clothes toWear) {
    if (toWear.wearOnce() == 0) this.remove(toWear);
  }

  public void useAllTools() {
    Product key = new Tool(1);
    if (contains(key))
      findProduct(key).clear();
  }
}
