package pl.edu.mimuw.trade.stock;

import pl.edu.mimuw.trade.agents.Agent;

import java.util.*;
import java.util.function.Consumer;

public class OfferQueue implements Iterable<Offer> {
  private final Agent issuer;
  private final SortedSet<Offer> offers;

  public OfferQueue(Agent issuer) {
    this.issuer = issuer;
    this.offers = new TreeSet<>(Offer::compareTo);
  }

  public void add(Offer offer) {
    offers.add(offer);
  }

  public void addAll(Collection<Offer> offers) {
    offers.forEach(this::add);
  }

  public Iterator<Offer> iterator() {
    return offers.iterator();
  }

  public void forEach(Consumer<? super Offer> action) {
    offers.forEach(action);
  }

  public Spliterator<Offer> spliterator() {
    return offers.spliterator();
  }

  public boolean isEmpty() {
    return offers.isEmpty();
  }

  public Agent issuer() {
    return issuer;
  }
}
