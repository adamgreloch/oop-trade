package pl.edu.mimuw.trade.simulation;

import pl.edu.mimuw.trade.agents.Agent;
import pl.edu.mimuw.trade.agents.Bank;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.Tradeable;
import pl.edu.mimuw.trade.strategy.stock.StockStrategy;

import java.util.*;

public class Stock {

  private transient final Bank bank;
  final StockLog log;
  private transient StockStrategy stockStrategy;
  private transient final Set<OfferQueue> workerOfferQueues;
  private transient final SortedSet<Offer> speculatorOffers;

  public Stock(StockStrategy stockStrategy, DayLog fallBack) {
    this.stockStrategy = stockStrategy;
    this.bank = new Bank();
    this.log = new StockLog(fallBack);
    this.workerOfferQueues = new HashSet<>();
    this.speculatorOffers = new TreeSet<>(Stock::compareBenefit);
  }

  public Stock(StockStrategy stockStrategy, StockLog log) {
    this.bank = new Bank();
    this.log = log;
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
    offers.forEach(e -> log.logOfferedQuantities(e.product, e.quantity()));
    if (issuer instanceof Worker) {
      OfferQueue queue = new OfferQueue(issuer);
      for (Offer offer : offers) {
        queue.add(offer);
        log.logWorkerSellOffered(offer.product, offer.quantity());
      }
      workerOfferQueues.add(queue);
    } else speculatorOffers.addAll(offers);
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
            found = bank.buyAll(workerOffer, log.previousLowest(workerOffer.product));
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

  public void logOfferedQuantities(Product levelled, int quantity) {
    log.logOfferedQuantities(levelled, quantity);
  }

  public int getOfferedQuantities(Product levelled, int day) {
    return log.getOfferedQuantities(levelled, day);
  }

  public double getAveragePrice(int day, Tradeable product) {
    return log.getAveragePrice(day, product);
  }

  public int getSoldQuantity(int day, Tradeable product) {
    return log.getSoldQuantity(day, product);
  }

  public int getWorkerSellOffered(Product product, int day) {
    return log.getWorkerSellOffered(product, day);
  }

  public String getDayLog() {
    return log.printCurrent();
  }

  public void setStrategy(StockStrategy strategy) {
    this.stockStrategy = strategy;
  }
}
