package pl.edu.mimuw.trade.stock;

import java.util.List;
import java.util.Set;

public interface StockStrategy {

  List<OfferQueue> sortWorkerOffers(Set<OfferQueue> workerOffers);
}
