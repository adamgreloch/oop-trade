package pl.edu.mimuw.trade.products;

import pl.edu.mimuw.trade.bag.Bag;

public interface StoredInBag {
  void addTo(Bag bag, int quantity);

  Tradeable takeFrom(Bag bag, int quantity);

  int getQuantityIn(Bag bag);
}
