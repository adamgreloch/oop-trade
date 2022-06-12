package pl.edu.mimuw.trade.simulation;

import pl.edu.mimuw.trade.agents.Agent;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.TreeSet;
import java.util.function.Consumer;

public class OfferQueue implements Iterable<Offer> {
  private final Agent issuer;
  private final SortedSet<Offer> offers;

  public OfferQueue(Agent issuer) {
    this.issuer = issuer;
    this.offers = new TreeSet<>(Offer::compareTo);
  }

  public void add(Offer offer) {
    this.offers.add(offer);
  }

  public Iterator<Offer> iterator() {
    return this.offers.iterator();
  }

  public void forEach(Consumer<? super Offer> action) {
    this.offers.forEach(action);
  }

  public Spliterator<Offer> spliterator() {
    return this.offers.spliterator();
  }

  public Agent issuer() {
    return this.issuer;
  }
}
