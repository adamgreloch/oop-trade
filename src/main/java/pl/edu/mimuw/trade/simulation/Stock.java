package pl.edu.mimuw.trade.simulation;

import pl.edu.mimuw.trade.agents.Agent;
import pl.edu.mimuw.trade.agents.Bank;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.strategy.stock.StockStrategy;

import java.util.*;

import static pl.edu.mimuw.trade.simulation.OfferType.SELL;

public class Stock {

  public final StockLog log;
  private transient final Bank bank;
  private transient final StockStrategy stockStrategy;
  private transient final Set<OfferQueue> workerOfferQueues;
  private transient final SortedSet<Offer> speculatorOffers;

  public Stock(StockStrategy stockStrategy, DayLog fallBack) {
    this.stockStrategy = stockStrategy;
    this.bank = new Bank();
    this.log = new StockLog(fallBack);
    this.workerOfferQueues = new HashSet<>();
    this.speculatorOffers = new TreeSet<>(Stock::compareBenefit);
  }

  /**
   * Sorts Speculator offers so that the most beneficial offer is first.
   */
  private static int compareBenefit(Offer a, Offer b) {
    int res = b.level() - a.level();
    if (res == 0) return (int) (b.price() - a.price());
    return res;
  }

  public void addOffer(Collection<Offer> offers, Agent issuer) {
    offers.forEach(e -> this.log.logOfferedQuantities(e.product, e.quantity()));
    if (issuer instanceof Worker) {
      OfferQueue queue = new OfferQueue(issuer);
      for (Offer offer : offers) {
        queue.add(offer);
        if (offer.offerType == SELL)
          this.log.logWorkerSellOffered(offer.product, offer.quantity());
      }
      this.workerOfferQueues.add(queue);
    }
    else this.speculatorOffers.addAll(offers);
  }

  void processTransactions() {
    List<OfferQueue> sorted = this.stockStrategy.sortWorkerOffers(this.workerOfferQueues);
    Offer found;

    for (OfferQueue workerOfferQueue : sorted) {
      Iterator<Offer> workerOfferQueueIterator = workerOfferQueue.iterator();
      Offer workerOffer = null;

      while (workerOfferQueueIterator.hasNext()) {
        if (workerOffer == null || workerOffer.isCompleted())
          workerOffer = workerOfferQueueIterator.next();

        found = this.findBestSpeculatorOffer(workerOffer);

        if (found == null) {
          if (workerOffer.offerType == SELL)
            found = this.bank.buyAll(workerOffer, this.log.previousLowest(workerOffer.product));
          else {
            this.workerOfferQueues.remove(workerOfferQueue);
            break;
          }
        }

        workerOffer.transaction(found, this.log);
        if (found.isCompleted()) this.speculatorOffers.remove(found);
      }
    }

    this.speculatorOffers.clear();
    this.workerOfferQueues.clear();
  }

  private Offer findBestSpeculatorOffer(Offer workerOffer) {
    for (Offer offer : this.speculatorOffers)
      if (offer.matches(workerOffer)) return offer;
    return null;
  }

  void newDay() {
    this.log.newDay();
  }
}
