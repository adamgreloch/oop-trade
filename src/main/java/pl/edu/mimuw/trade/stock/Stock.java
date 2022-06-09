package pl.edu.mimuw.trade.stock;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.agents.Agent;
import pl.edu.mimuw.trade.agents.Bank;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.Tradeable;
import pl.edu.mimuw.trade.strategy.stock.StockStrategy;

import java.util.*;

public class Stock {

  private transient final Bank bank;
  private final StockLog log;
  @SerializedName("giełda")
  private final StockStrategy stockStrategy;
  private transient final Set<OfferQueue> workerOfferQueues;
  private transient final SortedSet<Offer> speculatorOffers;

  public Stock(Simulation simulation, StockStrategy stockStrategy) {
    this.bank = new Bank(simulation);
    this.log = new StockLog();
    this.stockStrategy = stockStrategy;
    this.workerOfferQueues = new HashSet<>();
    this.speculatorOffers = new TreeSet<>(Stock::compareBenefit);
  }

  /**
   * Sort Speculator offers in such way, that the most beneficial one is first in the iterator.
   */
  private static int compareBenefit(Offer a, Offer b) {
    int res = b.level() - a.level();
    if (res == 0) return (int) (b.price() - a.price());
    return res;
  }

  public void addOffer(Set<Offer> offers, Agent issuer) {
    if (issuer instanceof Worker) {
      OfferQueue queue = new OfferQueue(issuer);
      queue.addAll(offers);
      workerOfferQueues.add(queue);
    }
    else speculatorOffers.addAll(offers);
  }

  void processTransactions() {
    List<OfferQueue> sorted = stockStrategy.sortWorkerOffers(workerOfferQueues);
    Offer found;
    for (OfferQueue workerOfferQueue : sorted) {
      Iterator<Offer> workerOfferQueueIterator = workerOfferQueue.iterator();
      Offer workerOffer = null;
      while (workerOfferQueueIterator.hasNext()) {
        if (workerOffer == null || workerOffer.isCompleted())
          workerOffer = workerOfferQueueIterator.next();

        found = findBestSpeculatorOffer(workerOffer);
        if (found == null) {
          if (workerOffer.offerType == OfferType.SELL)
            found = bank.buyAll(workerOffer, log.previousLowest(workerOffer.product()));
          else {
            workerOfferQueues.remove(workerOfferQueue);
            break;
          }
        }

        workerOffer.transaction(found, log);
        if (found.isCompleted()) speculatorOffers.remove(found);
      }
    }
    speculatorOffers.clear();
    workerOfferQueues.clear();
  }

  private Offer findBestSpeculatorOffer(Offer workerOffer) {
    for (Offer offer : speculatorOffers)
      if (offer.matches(workerOffer))
        return offer;
    return null;
  }

  public void setFallBackPrices(double food, double clothes, double tools, double programs) {
    log.setFallBackPrices(food, clothes, tools, programs);
  }

  void newDay() {
    log.newDay();
  }

  public double getAveragePrice(int day, Tradeable product) {
    return log.getAveragePrice(day, product);
  }

  public int getSoldQuantity(int day, Tradeable product) {
    return log.getSoldQuantity(day, product);
  }

  public String getDayLog() {
    return log.printCurrent();
  }
}
