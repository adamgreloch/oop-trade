package pl.edu.mimuw.stock;

import pl.edu.mimuw.Simulation;
import pl.edu.mimuw.agents.Agent;
import pl.edu.mimuw.agents.Bank;
import pl.edu.mimuw.agents.Worker;
import pl.edu.mimuw.products.TradeableProduct;

import java.util.*;

public class Stock {

  private final Bank bank;
  private final Log log;
  private final StockStrategy stockStrategy;
  private final Set<OfferQueue> workerOfferQueues;
  private final SortedSet<Offer> speculatorOffers;

  public Stock(Simulation simulation, StockStrategy stockStrategy) {
    this.bank = new Bank(simulation);
    this.log = new Log();
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

  public void processTransactions() {
    List<OfferQueue> sorted = stockStrategy.sortWorkerOffers(workerOfferQueues);
    Offer found;
    for (OfferQueue workerOfferQueue : sorted) {
      for (Offer workerOffer : workerOfferQueue) {
        found = findBestSpeculatorOffer(workerOffer.product());
        if (found == null) {
          if (!workerOffer.isPurchaseOffer)
            found = bank.buyAll(workerOffer, log.previousLowest(workerOffer.product()));
          else {
            workerOfferQueues.remove(workerOfferQueue);
            break;
          }
        }
        workerOffer.transaction(found, log);
      }
    }
    speculatorOffers.clear();
    workerOfferQueues.clear();
  }

  private Offer findBestSpeculatorOffer(TradeableProduct interest) {
    for (Offer offer : speculatorOffers) {
      if (offer.product().equals(interest))
        return offer;
    }
    return null;
  }

  public void setFallBackPrices(double food, double clothes, double tools, double programs) {
    log.setFallBackPrices(food, clothes, tools, programs);
  }
}
