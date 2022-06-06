package pl.edu.mimuw.stock;

import pl.edu.mimuw.Simulation;
import pl.edu.mimuw.agents.Agent;
import pl.edu.mimuw.agents.Bank;
import pl.edu.mimuw.agents.Worker;
import pl.edu.mimuw.products.TradeableProduct;

import java.util.*;

public class Stock {

  private final Bank bank;
  private final History history;
  private final StockStrategy stockStrategy;
  private final Set<OfferQueue> workerOfferQueues;
  private final SortedSet<Offer> speculatorOffers;

  /**
   * Sort Speculator offers in such way, that the most beneficial one is first in the iterator.
   */
  private static int compareBenefit(Offer a, Offer b) {
    int res = b.level() - a.level();
    if (res == 0) return (int) (b.price() - a.price());
    return res;
  }

  public Stock(Simulation simulation, StockStrategy stockStrategy) {
    this.bank = new Bank(simulation);
    this.history = new History();
    this.stockStrategy = stockStrategy;
    this.workerOfferQueues = new HashSet<>();
    this.speculatorOffers = new TreeSet<>(Stock::compareBenefit);
  }

  public void addOffer(Set<Offer> offers, Agent issuer) {
    if (issuer instanceof Worker) {
      OfferQueue queue = new OfferQueue(issuer);
      queue.addAll(offers);
      workerOfferQueues.add(queue);
    } else speculatorOffers.addAll(offers);
  }

  public void processTransactions() {
    List<OfferQueue> sorted = stockStrategy.sortWorkerOffers(workerOfferQueues);
    Offer found;
    for (OfferQueue workerOfferQueue : sorted) {
      for (Offer workerOffer : workerOfferQueue) {
        found = findBestSpeculatorOffer(workerOffer.product());
        if (found == null) {
          if (!workerOffer.isPurchaseOffer)
            bank.buyAll(workerOffer, history.previousLowest(workerOffer.product()));
          else {
            workerOfferQueues.remove(workerOfferQueue);
            break;
          }
        }
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
}
