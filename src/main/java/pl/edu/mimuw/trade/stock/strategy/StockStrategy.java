package pl.edu.mimuw.trade.stock.strategy;

import pl.edu.mimuw.trade.stock.OfferQueue;

import java.util.List;
import java.util.Set;

public interface StockStrategy {

  List<OfferQueue> sortWorkerOffers(Set<OfferQueue> workerOffers);
}
